package com.kohler.arscan;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.xiuyukeji.pictureplayerview.PicturePlayerView;

import java.io.File;
import java.io.FilenameFilter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PlayActivity extends AppCompatActivity {

    private final static String TAG = PlayActivity.class.getSimpleName();

    @BindView(R2.id.play_view)
    PicturePlayerView play_view;

    private String startWith;
    private String zhName;
    private String enName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        ButterKnife.bind(this);

        initData();
        initView();
    }

    private void initView() {

    }

    private void initData() {
        int flag = getIntent().getIntExtra("flag", 0);

        switch (flag) {
            case 0:
                startWith = "0_";
                break;
            case 1:
                startWith = "1_";
                break;
            case 2:
                startWith = "2_";
                break;
            case 3:
                startWith = "3_";
                break;
            case 4:
                startWith = "4_";
                break;
            case 5:
                startWith = "5_";
                break;
            case 6:
                startWith = "6_";
                break;
            case 7:
                startWith = "7_";
                break;
        }

        File file = new File(Environment.getExternalStorageDirectory() + "/resource/image/");
        FilenameFilter filter = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.startsWith(startWith);
            }
        };
        File[] files = file.listFiles(filter);
        String[] paths = new String[files.length];
        for (int i = 0; i < files.length; i++) {
            paths[i] = files[i].getAbsolutePath();
            Log.e(TAG, "path: " + paths[i]);
        }

        play_view.setDataSource(paths, files.length * 1000 / 30);
        play_view.start();

    }

}
