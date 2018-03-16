package com.mengyang.kohler.home.activity;

import com.mengyang.kohler.BaseActivity;
import com.mengyang.kohler.R;
import com.mengyang.kohler.common.net.DefaultObserver;
import com.mengyang.kohler.common.net.IdeaApi;
import com.mengyang.kohler.home.adapter.MyImageAdapter;
import com.mengyang.kohler.home.view.PhotoViewPager;
import com.mengyang.kohler.module.BasicResponse;
import com.mengyang.kohler.module.bean.ArtKohlerSelectImgBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ArtKohlerSelectBigImgActivity extends BaseActivity {
    @BindView(R.id.vp_art_kohler_select_big_img)
    PhotoViewPager vpArtKohlerSelectBigImg;
    private List<ArtKohlerSelectImgBean.ResultListBean> mArtSelectBean = new ArrayList<>();
    private int position;

    private MyImageAdapter adapter;
    private List<String> Urls;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_art_kohler_select_big_img;
    }

    @Override
    protected void initValues() {
        position = getIntent().getIntExtra("position", 0);
        Urls = new ArrayList<>();
    }

    @Override
    protected void initListener() {
    }

    @Override
    protected void initData() {
        Map<String, String> stringMap = IdeaApi.getSign();
        stringMap.put("groupId", getIntent().getIntExtra("select_img", 0) + "");

        IdeaApi.getRequestLogin(stringMap);
        IdeaApi.getApiService()
                .getArtKohlerSelectImg(stringMap)
                .compose(ArtKohlerSelectBigImgActivity.this.<BasicResponse<ArtKohlerSelectImgBean>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse<ArtKohlerSelectImgBean>>(ArtKohlerSelectBigImgActivity.this, false) {
                    @Override
                    public void onSuccess(BasicResponse<ArtKohlerSelectImgBean> response) {
                        mArtSelectBean.clear();
                        mArtSelectBean.addAll(response.getData().getResultList());
                        for (int i = 0; i < mArtSelectBean.size(); i++) {
                            if (mArtSelectBean.get(i).getPicUrl() != null) {
                                Urls.add(mArtSelectBean.get(i).getPicUrl());
                            }
                        }
                        adapter = new MyImageAdapter(Urls, ArtKohlerSelectBigImgActivity.this);
                        vpArtKohlerSelectBigImg.setAdapter(adapter);
                        vpArtKohlerSelectBigImg.setCurrentItem(position, false);
                    }
                });
    }
}
