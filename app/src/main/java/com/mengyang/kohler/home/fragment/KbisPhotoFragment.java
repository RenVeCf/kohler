package com.mengyang.kohler.home.fragment;

import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
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
import com.mengyang.kohler.common.utils.ToastUtil;
import com.mengyang.kohler.common.view.GridSpacingItemDecoration;
import com.mengyang.kohler.common.view.SpacesItemDecoration;
import com.mengyang.kohler.home.activity.WeeklyRadioConcertActivity;
import com.mengyang.kohler.home.adapter.KbisPdfAdapter;
import com.mengyang.kohler.home.adapter.KbisPhotoAdapter;
import com.mengyang.kohler.module.BasicResponse;
import com.mengyang.kohler.module.bean.KbisBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class KbisPhotoFragment extends BaseFragment implements BaseQuickAdapter.OnItemChildClickListener {

    @BindView(R.id.rv_kbis_photo)
    RecyclerView mRvKbisPhoto;

    private KbisBean mResultBean;
    private List<KbisBean.PhotoListBean> mKbisPhotoBean = new ArrayList<>();
    private KbisPhotoAdapter mKbisPhotoAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_kbis_photo;
    }

    @Override
    protected void initValues() {
        final StaggeredGridLayoutManager layoutManagerActivity = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRvKbisPhoto.setLayoutManager(layoutManagerActivity);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        KbisBean data = (KbisBean) getArguments().getSerializable("data");
        mKbisPhotoBean.clear();
        mKbisPhotoBean.addAll(data.getPhotoList());
        mKbisPhotoAdapter = new KbisPhotoAdapter(mKbisPhotoBean);
        mRvKbisPhoto.setAdapter(mKbisPhotoAdapter);
        mKbisPhotoAdapter.setOnItemChildClickListener(KbisPhotoFragment.this);
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        String h5Url = mKbisPhotoBean.get(position).getH5Url();
        if (!TextUtils.isEmpty(h5Url)) {
            startActivity(new Intent(KbisPhotoFragment.this.getActivity(), WebViewActivity.class).putExtra("h5url", h5Url));
        } else {
            ToastUtil.showToast("h5Url url is null");
        }
    }
}
