package com.kohler.arscan;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.Timer;
import java.util.TimerTask;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShowActivity extends AppCompatActivity {

    private final static String TAG = ShowActivity.class.getSimpleName();

    private DownloadManager downloadManager;
    private long downloadId;
    private Timer timer;
    private TimerTask timerTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        ButterKnife.bind(this);

        downZip();
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle bundle = msg.getData();
            int pro = bundle.getInt("pro");
            Log.e(TAG, "progress: " + pro);
        }
    };

    private void downZip() {
        String zipUrl = "http://p4n3tf891.bkt.clouddn.com/resource.zip";

        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(zipUrl));

        request.setDestinationInExternalFilesDir(this, Environment.DIRECTORY_DOWNLOADS, "resource.zip");

        /*
         * 设置在通知栏是否显示下载通知(下载进度), 有 3 个值可选:
         *    VISIBILITY_VISIBLE:                   下载过程中可见, 下载完后自动消失 (默认)
         *    VISIBILITY_VISIBLE_NOTIFY_COMPLETED:  下载过程中和下载完成后均可见
         *    VISIBILITY_HIDDEN:                    始终不显示通知
         */
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN);

        //设置通知栏中下载通知的显示样式
        request.setMimeType("application/x-zip-compressed");

       /*
        * 设置允许使用的网络类型, 可选值:
        *     NETWORK_MOBILE:      移动网络
        *     NETWORK_WIFI:        WIFI网络
        *     NETWORK_BLUETOOTH:   蓝牙网络
        * 默认为所有网络都允许
        */
        // request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);

        downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);

        //将下载请求加入下载队列, 返回一个下载ID，通过此ID可以查询数据。
        downloadId = downloadManager.enqueue(request);
        Log.e(TAG, "downloadId: " + downloadId);

        // 如果中途想取消下载, 可以调用remove方法, 根据返回的下载ID取消下载, 取消下载后下载保存的文件将被删除
        // downloadManager.remove(downloadId);

        final DownloadManager.Query query = new DownloadManager.Query();

        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                Cursor cursor = downloadManager.query(query.setFilterById(downloadId));
                if (cursor != null && cursor.moveToFirst()) {
                    if (cursor.getInt(
                            cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)) == DownloadManager.STATUS_SUCCESSFUL) {
                        boolean unZip = unZip(cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_FILENAME)));
                        Log.e(TAG, "解压: " + unZip);
                        timerTask.cancel();
                    }
                    //下载的文件到本地的目录
//                    String address = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
                    //已经下载的字节数
                    long bytes_downloaded = cursor.getLong(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                    //总需下载的字节数
                    long bytes_total = cursor.getLong(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
                    //Notification 标题
//                    String title = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_TITLE));
                    //描述
//                    String description = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_DESCRIPTION));
                    //下载对应id
//                    long id = cursor.getLong(cursor.getColumnIndex(DownloadManager.COLUMN_ID));
                    //下载文件名称
//                    String filename = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_FILENAME));
                    //下载文件的URL链接
//                    String url = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_URI));

                    //address: file:///storage/emulated/0/Android/data/com.kohler.ar/files/Download/resource-1.zip
                    // --title: resource-1.zip
                    // --description:
                    // --id: 639
                    // --filename: /storage/emulated/0/Android/data/com.kohler.ar/files/Download/resource-1.zip
                    // --url: http://p4n3tf891.bkt.clouddn.com/resource.zip

//                    Log.e(TAG, "address: " + address + "--title: " + title
//                            + "--description: " + description + "--id: " + id
//                            + "--filename: " + filename + "--url: " + url);

                    Log.e(TAG, "bytes_downloaded: " + bytes_downloaded + "--bytes_total: " + bytes_total);
                    int pro = (int) ((bytes_downloaded * 100) / bytes_total);
                    Message msg = Message.obtain();
                    Bundle bundle = new Bundle();
                    bundle.putInt("pro", pro);
                    msg.setData(bundle);
                    handler.sendMessage(msg);
                }
                cursor.close();
            }
        };
        timer.schedule(timerTask, 0, 1000);
    }

    private boolean unZip(String path) {
        Log.e(TAG, "unZip: " + path);
        String folderPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator;
        File file = new File(path);
        ZipFile zFile = null;
        try {
            zFile = new ZipFile(file);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        Enumeration zList = zFile.entries();
        ZipEntry zipEntry = null;
        byte[] buf = new byte[1024];
        while (zList.hasMoreElements()) {
            zipEntry = (ZipEntry) zList.nextElement();
            Log.e(TAG, "zipEntry: " + zipEntry.getName());
            // 列举的压缩文件里面的各个文件，判断是否为目录
            if (zipEntry.isDirectory()) {
                String dirstr = folderPath + zipEntry.getName();
                Log.e(TAG, "dirstr: " + dirstr);
                dirstr.trim();
                File f = new File(dirstr);
                f.mkdir();
                continue;
            }
            OutputStream os = null;
            FileOutputStream fos = null;
            // zipEntry.getName()会返回 script/start.script这样的，是为了返回实体的File
            File realFile = getRealFileName(folderPath, zipEntry.getName());
            try {
                fos = new FileOutputStream(realFile);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return false;
            }
            os = new BufferedOutputStream(fos);
            InputStream is = null;
            try {
                is = new BufferedInputStream(zFile.getInputStream(zipEntry));
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
            int readLen = 0;
            // 进行一些内容复制操作
            try {
                while ((readLen = is.read(buf, 0, 1024)) != -1) {
                    os.write(buf, 0, readLen);
                }
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
            try {
                is.close();
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        try {
            zFile.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        if (file.exists()) {
            boolean delete = file.delete();
            Log.e(TAG, "删除: " + delete);
        }
        return true;
    }

    /**
     * 给定根目录，返回一个相对路径所对应的实际文件名.
     *
     * @param baseDir     指定根目录
     * @param absFileName 相对路径名，来自于ZipEntry中的name
     * @return java.io.File 实际的文件
     */
    public static File getRealFileName(String baseDir, String absFileName) {
        Log.e(TAG, "baseDir=" + baseDir + "------absFileName="
                + absFileName);
        absFileName = absFileName.replace("\\", "/");
        Log.e(TAG, "absFileName=" + absFileName);
        String[] dirs = absFileName.split("/");
        Log.e(TAG, "dirs=" + dirs.toString());
        File ret = new File(baseDir);
        String substr = null;
        if (dirs.length > 1) {
            for (int i = 0; i < dirs.length - 1; i++) {
                substr = dirs[i];
                ret = new File(ret, substr);
            }

            if (!ret.exists())
                ret.mkdirs();
            substr = dirs[dirs.length - 1];
            ret = new File(ret, substr);
            return ret;
        } else {
            ret = new File(ret, absFileName);
        }
        return ret;
    }

    @OnClick(R2.id.iv_top_back)
    public void back() {
        finish();
    }

    @OnClick(R2.id.iv_top_scan)
    public void scan() {
        Intent intent = new Intent(this, UnityPlayerActivity.class);
        intent.putExtra("flag", "9");
        startActivity(intent);
    }

    @OnClick(R2.id.ll_main)
    public void main() {
        //        if (position == 1 && SPUtil.get(this, IConstants.TYPE, "").equals("dealer")) {
        //            startActivity(new Intent(this, MineManualActivity.class));
        //        }
    }

    @OnClick(R2.id.ll_search)
    public void search() {
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }

    @OnClick(R2.id.ll_notebook)
    public void notebook() {
        //        if (((boolean) SPUtil.get(this, IConstants.IS_LOGIN, false))) {
        //            if (SPUtil.get(this, IConstants.TYPE, "").equals("dealer")) {
        //                startActivity(new Intent(this, MineManualActivity.class));
        //            }
        //        } else {
        //            startActivity(new Intent(this, LoginActivity.class));
        //        }
    }

}