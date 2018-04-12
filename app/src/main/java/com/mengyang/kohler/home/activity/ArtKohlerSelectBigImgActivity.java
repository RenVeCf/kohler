package com.mengyang.kohler.home.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.mengyang.kohler.BaseActivity;
import com.mengyang.kohler.R;
import com.mengyang.kohler.common.net.DefaultObserver;
import com.mengyang.kohler.common.net.IdeaApi;
import com.mengyang.kohler.common.utils.ToastUtil;
import com.mengyang.kohler.home.adapter.MyImageAdapter;
import com.mengyang.kohler.home.view.BottomAnimDialog;
import com.mengyang.kohler.home.view.PhotoViewPager;
import com.mengyang.kohler.module.BasicResponse;
import com.mengyang.kohler.module.bean.ArtKohlerSelectImgBean;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ArtKohlerSelectBigImgActivity extends BaseActivity implements ViewPager.OnPageChangeListener {
    @BindView(R.id.vp_art_kohler_select_big_img)
    PhotoViewPager vpArtKohlerSelectBigImg;
    @BindView(R.id.tv_page_num)
    TextView mTvPageNum;
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

        vpArtKohlerSelectBigImg.addOnPageChangeListener(this);
    }

    @Override
    protected void initListener() {
    }

    @Override
    protected void initData() {
        Map<String, Object> stringMap = IdeaApi.getSign();
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

                        adapter.SetPictureLongClickListenner(new MyImageAdapter.PictureLongClickListenner() {
                            @Override
                            public void onPictureLongClick(final String url) {
                                final BottomAnimDialog dialog = new BottomAnimDialog(ArtKohlerSelectBigImgActivity.this, "保存图片", "取消");
                                dialog.setClickListener(new BottomAnimDialog.BottonAnimDialogListener() {
                                    @Override
                                    public void onItem1Listener() {
                                        // TO_DO
                                        new Thread() {
                                            @Override
                                            public void run() {
                                                super.run();
                                                Bitmap bitmap = returnBitMap(url);
                                                MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "title", "description");
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        ToastUtil.showToast("图片已保存！");
                                                    }
                                                });
                                            }
                                        }.start();
                                        dialog.dismiss();
                                    }

                                    @Override
                                    public void onItem3Listener() {
                                        dialog.dismiss();
                                    }
                                });
                                dialog.show();


                            }
                        });
                    }
                });
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mTvPageNum.setText((position + 1) + "/" + Urls.size());
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public Bitmap returnBitMap(String url) {
        URL myFileUrl = null;
        Bitmap bitmap = null;
        try {
            myFileUrl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}
