package com.mengyang.kohler.home.fragment;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.mengyang.kohler.App;
import com.mengyang.kohler.BaseFragment;
import com.mengyang.kohler.R;
import com.mengyang.kohler.common.net.DefaultObserver;
import com.mengyang.kohler.common.net.IdeaApi;
import com.mengyang.kohler.common.view.GridSpacingItemDecoration;
import com.mengyang.kohler.home.adapter.KbisAgendadapter;
import com.mengyang.kohler.home.adapter.MeetingAdapter;
import com.mengyang.kohler.module.BasicResponse;
import com.mengyang.kohler.module.bean.KbisBean;
import com.mengyang.kohler.module.bean.MeetingBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 2018科勒上海厨卫展——议程
 */

public class KbisAgendaFragment extends BaseFragment {

    @BindView(R.id.rv_kbis_agenda)
    RecyclerView rvKbisAgenda;
    private KbisBean mMeetingBean;
    private List<KbisBean.AgendaListBean> mMeetingAdapterBean;
    private KbisAgendadapter mKbisAgendadapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_kbis_agenda;
    }

    @Override
    protected void initValues() {
        GridLayoutManager layoutManagerActivity = new GridLayoutManager(App.getContext(), 2);
        rvKbisAgenda.setLayoutManager(layoutManagerActivity);
        rvKbisAgenda.addItemDecoration(new GridSpacingItemDecoration(2, 20, false));
        rvKbisAgenda.setHasFixedSize(true);
        rvKbisAgenda.setItemAnimator(new DefaultItemAnimator());
        rvKbisAgenda.setNestedScrollingEnabled(false);

        mMeetingAdapterBean = new ArrayList<>();
        mKbisAgendadapter = new KbisAgendadapter(mMeetingAdapterBean);
        rvKbisAgenda.setAdapter(mKbisAgendadapter);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        IdeaApi.getApiService()
                .getKbis()
                .compose(KbisAgendaFragment.this.<BasicResponse<KbisBean>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse<KbisBean>>(getActivity(), true) {
                    @Override
                    public void onSuccess(BasicResponse<KbisBean> response) {
                        mMeetingBean = response.getData();

                        KbisBean.AgendaListBean first = mMeetingBean.getAgendaList().get(0);

                        mMeetingAdapterBean.clear();
                        mMeetingAdapterBean.addAll(mMeetingBean.getAgendaList());
                        mMeetingAdapterBean.add(0, first);

                        mKbisAgendadapter = new KbisAgendadapter(mMeetingAdapterBean);
                        rvKbisAgenda.setAdapter(mKbisAgendadapter);
                    }
                });
    }
}
