package com.hiar.sdk.utils;

import android.content.Context;
import android.content.res.Resources;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileUtils {

    // 从sh脚本中加载shader内容的方法
    public static String loadFromAssetsFile(String fname, Resources r) {
        String result = null;
        try {
            InputStream in = r.getAssets().open(fname);
            int ch = 0;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            while ((ch = in.read()) != -1) {
                baos.write(ch);
            }
            byte[] buff = baos.toByteArray();
            baos.close();
            in.close();
            result = new String(buff, "UTF-8");
            result = result.replaceAll("\\r\\n", "\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    // needRefresh 是否需要更新本地已存在的文件
    public static boolean CopyAssets2SDcard(Context context, String assetDir, String dir, boolean needRefresh) {
        String[] files;

        try {
            files = context.getResources().getAssets().list(assetDir);
            File mWorkingPath = new File(dir);

            if (!mWorkingPath.exists()) {
                if (!mWorkingPath.mkdirs()) {

                }
            }
            for (int i = 0; i < files.length; i++) {
                String fileName = files[i];
                // we make sure file name not contains '.' to be a folder.
                if (!fileName.contains(".")) {
                    if (0 == assetDir.length()) {
                        CopyAssets2SDcard(context, fileName, dir + fileName + "/", needRefresh);
                    } else {
                        CopyAssets2SDcard(context, assetDir + "/" + fileName, dir + fileName + "/", needRefresh);
                    }
                    continue;
                }
                File outFile = new File(mWorkingPath, fileName);
                if (outFile.exists()) {
                    if (needRefresh) {
                        outFile.delete();
                    } else {
                        return true;
                    }
                }
                InputStream in = null;
                if (0 != assetDir.length()) {
                    in = context.getAssets().open(assetDir + "/" + fileName);
                } else {
                    in = context.getAssets().open(fileName);
                }
                OutputStream out = new FileOutputStream(outFile);

                // Transfer bytes from in to out
                byte[] buf = new byte[2048];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }

                in.close();
                out.close();
            }
        } catch (IOException e1) {
            return false;
        }
        return true;
    }
}
