package com.mengyang.kohler.home.activity;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gyf.barlibrary.ImmersionBar;
import com.mengyang.kohler.App;
import com.mengyang.kohler.BaseActivity;
import com.mengyang.kohler.R;
import com.mengyang.kohler.common.net.DefaultObserver;
import com.mengyang.kohler.common.net.IdeaApi;
import com.mengyang.kohler.common.view.GridSpacingItemDecoration;
import com.mengyang.kohler.common.view.TopView;
import com.mengyang.kohler.home.adapter.ArtKohlerSelectActivityAdapter;
import com.mengyang.kohler.home.view.ImageInfoObj;
import com.mengyang.kohler.home.view.ImageWidgetInfoObj;
import com.mengyang.kohler.module.BasicResponse;
import com.mengyang.kohler.module.bean.ArtKohlerSelectImgBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 精选图片
 */

public class ArtKohlerSelectActivity extends BaseActivity {

    @BindView(R.id.tv_art_kohler_select_top)
    TopView tvArtKohlerSelectTop;
    @BindView(R.id.rv_art_kohler_select)
    RecyclerView rvArtKohlerSelect;
    @BindView(R.id.srl_art_kohler_select)
    SwipeRefreshLayout srlArtKohlerSelect;
    private ArtKohlerSelectActivityAdapter mArtKohlerSelectActivityAdapter;
    private List<ArtKohlerSelectImgBean.ResultListBean> mArtSelectBean;
    private ImageInfoObj imageInfoObj;
    private ImageWidgetInfoObj imageWidgetInfoObj;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_art_kohler_select;
    }

    @Override
    protected void initValues() {
        App.getManager().addActivity(this);
        //防止状态栏和标题重叠
        ImmersionBar.setTitleBar(this, tvArtKohlerSelectTop);

        GridLayoutManager layoutManagerActivity = new GridLayoutManager(App.getContext(), 3);
        rvArtKohlerSelect.setLayoutManager(layoutManagerActivity);
        rvArtKohlerSelect.addItemDecoration(new GridSpacingItemDecoration(3, 15, false));
        rvArtKohlerSelect.setHasFixedSize(true);
        rvArtKohlerSelect.setItemAnimator(new DefaultItemAnimator());

        mArtSelectBean = new ArrayList<>();
        mArtKohlerSelectActivityAdapter = new ArtKohlerSelectActivityAdapter(mArtSelectBean);
        rvArtKohlerSelect.setAdapter(mArtKohlerSelectActivityAdapter);
    }

    @Override
    protected void initListener() {
        srlArtKohlerSelect.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
                srlArtKohlerSelect.setRefreshing(false);
            }
        });
    }

    @Override
    protected void initData() {
        Map<String, String> stringMap = IdeaApi.getSign();
        stringMap.put("groupId", "2");

        IdeaApi.getRequestLogin(stringMap);
        IdeaApi.getApiService()
                .getArtKohlerSelectImg(stringMap)
                .compose(ArtKohlerSelectActivity.this.<BasicResponse<ArtKohlerSelectImgBean>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse<ArtKohlerSelectImgBean>>(ArtKohlerSelectActivity.this, true) {
                    @Override
                    public void onSuccess(BasicResponse<ArtKohlerSelectImgBean> response) {
                        mArtSelectBean.clear();
                        mArtSelectBean.addAll(response.getData().getResultList());
                        mArtKohlerSelectActivityAdapter = new ArtKohlerSelectActivityAdapter(mArtSelectBean);
                        rvArtKohlerSelect.setAdapter(mArtKohlerSelectActivityAdapter);
                        mArtKohlerSelectActivityAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                imageInfoObj = new ImageInfoObj();
                                imageInfoObj.imageUrl = mArtSelectBean.get(position).getPicUrl();
                                imageInfoObj.imageWidth = 1280;
                                imageInfoObj.imageHeight = 720;

                                imageWidgetInfoObj = new ImageWidgetInfoObj();
                                imageWidgetInfoObj.x = view.getLeft();
                                imageWidgetInfoObj.y = view.getTop();
                                imageWidgetInfoObj.width = view.getLayoutParams().width;
                                imageWidgetInfoObj.height = view.getLayoutParams().height;

                                startActivity(new Intent(ArtKohlerSelectActivity.this, ArtKohlerSelectBigImgActivity.class).putExtra("imageInfoObj", imageInfoObj).putExtra("imageWidgetInfoObj", imageWidgetInfoObj));
                            }
                        });
                    }
                });
    }
}
