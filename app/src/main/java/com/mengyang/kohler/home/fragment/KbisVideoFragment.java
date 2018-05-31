package com.mengyang.kohler.home.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.mengyang.kohler.App;
import com.mengyang.kohler.BaseFragment;
import com.mengyang.kohler.R;
import com.mengyang.kohler.common.activity.WebViewActivity;
import com.mengyang.kohler.common.net.DefaultObserver;
import com.mengyang.kohler.common.net.IdeaApi;
import com.mengyang.kohler.common.utils.LogUtils;
import com.mengyang.kohler.common.utils.ToastUtil;
import com.mengyang.kohler.common.view.GridSpacingItemDecoration;
import com.mengyang.kohler.common.view.SpacesItemDecoration;
import com.mengyang.kohler.home.adapter.KbisVideoAdapter;
import com.mengyang.kohler.module.BasicResponse;
import com.mengyang.kohler.module.bean.KbisBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class KbisVideoFragment extends BaseFragment {
    @BindView(R.id.rv_kbis_video)
    RecyclerView mRvKbisVideo;

    private List<KbisBean.VideoListBean> mKbisVideoBean = new ArrayList<>();
    private KbisVideoAdapter mKbisVideoAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_kbis_video;
    }

    @Override
    protected void initValues() {
        mRvKbisVideo.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        SpacesItemDecoration decoration = new SpacesItemDecoration(25);
        mRvKbisVideo.addItemDecoration(decoration);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        KbisBean data = (KbisBean) getArguments().getSerializable("data");
        mKbisVideoBean.clear();
        mKbisVideoBean.addAll(data.getVideoList());
        mKbisVideoAdapter = new KbisVideoAdapter(mKbisVideoBean);
        mRvKbisVideo.setAdapter(mKbisVideoAdapter);
        mKbisVideoAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                String h5Url = mKbisVideoBean.get(position).getH5Url();
                if (!TextUtils.isEmpty(h5Url)) {
                    startActivity(new Intent(KbisVideoFragment.this.getActivity(), WebViewActivity.class).putExtra("h5url", h5Url));
                } else {
                    ToastUtil.showToast("h5Url url is null");
                }
            }
        });
    }
}
