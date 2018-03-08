package com.mengyang.kohler.home.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.mengyang.kohler.R;
import com.mengyang.kohler.common.utils.StringUtilss;
import com.mengyang.kohler.home.adapter.ImageViewPagerAdapter;
import com.mengyang.kohler.home.view.HackyViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * author：Geoffery Sun
 * time：2016/12/25 18:37
 * desc：
 * version：0.0.1
 */
public class ShowImgActivity extends AppCompatActivity {
    private ImageViewPagerAdapter adapter;
    HackyViewPager pager;
    private List<String> mList = new ArrayList<>();
    String mInfo = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_showimg);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            if (bundle.containsKey("info")) {
                mInfo = bundle.getString("info");
            }
        }

        if (StringUtilss.isEmpty(mInfo)) {
            finish();
        } else {
            pager = (HackyViewPager) findViewById(R.id.pager);
            mList.add(mInfo);
            adapter = new ImageViewPagerAdapter(getSupportFragmentManager(), mList);
            pager.setAdapter(adapter);
        }
    }
}
