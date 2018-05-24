package com.mengyang.kohler.home.fragment;

import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.mengyang.kohler.App;
import com.mengyang.kohler.BaseFragment;
import com.mengyang.kohler.R;
import com.mengyang.kohler.common.activity.WebViewActivity;
import com.mengyang.kohler.common.net.DefaultObserver;
import com.mengyang.kohler.common.net.IdeaApi;
import com.mengyang.kohler.common.utils.ToastUtil;
import com.mengyang.kohler.common.view.GridSpacingItemDecoration;
import com.mengyang.kohler.home.adapter.KbisPhotoAdapter;
import com.mengyang.kohler.home.adapter.KbisVideoAdapter;
import com.mengyang.kohler.module.BasicResponse;
import com.mengyang.kohler.module.bean.KbisBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class KbisVideoFragment extends BaseFragment implements BaseQuickAdapter.OnItemChildClickListener {
    @BindView(R.id.rv_kbis_video)
    RecyclerView mRvKbisVideo;

    private KbisBean mResultBean;
    private List<KbisBean.VideoListBean> mKbisVideoBean = new ArrayList<>();
    private KbisVideoAdapter mKbisVideoAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_kbis_video;
    }

    @Override
    protected void initValues() {
        GridLayoutManager layoutManagerActivity = new GridLayoutManager(App.getContext(), 2);
        mRvKbisVideo.setLayoutManager(layoutManagerActivity);
        mRvKbisVideo.addItemDecoration(new GridSpacingItemDecoration(2, 20, false));
        mRvKbisVideo.setHasFixedSize(true);
        mRvKbisVideo.setItemAnimator(new DefaultItemAnimator());
        mRvKbisVideo.setNestedScrollingEnabled(false);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        IdeaApi.getApiService()
                .getKbis()
                .compose(KbisVideoFragment.this.<BasicResponse<KbisBean>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse<KbisBean>>(getActivity(), true) {
                    @Override
                    public void onSuccess(BasicResponse<KbisBean> response) {
                        mResultBean = response.getData();

                        mKbisVideoBean.clear();
                        mKbisVideoBean.addAll(mResultBean.getVideoList());

                        mKbisVideoAdapter = new KbisVideoAdapter(mKbisVideoBean);
                        mRvKbisVideo.setAdapter(mKbisVideoAdapter);
                        mKbisVideoAdapter.setOnItemChildClickListener(KbisVideoFragment.this);
                    }
                });
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        String h5Url = mKbisVideoBean.get(position).getH5Url();
        if (!TextUtils.isEmpty(h5Url)) {
            startActivity(new Intent(KbisVideoFragment.this.getActivity(), WebViewActivity.class).putExtra("h5url", h5Url));
        } else {
            ToastUtil.showToast("h5Url url is null");
        }
    }
}
