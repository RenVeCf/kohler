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

    @BindView(R.id.tv_kbis_position_zero_agenda_type)
    TextView tvKbisPositionZeroAgendaType;
    @BindView(R.id.tv_kbis_position_zero_agenda_time)
    TextView tvKbisPositionZeroAgendaTime;
    @BindView(R.id.tv_kbis_position_zero_agenda_position)
    TextView tvKbisPositionZeroAgendaPosition;
    @BindView(R.id.tv_kbis_position_zero_agenda_name)
    TextView tvKbisPositionZeroAgendaName;
    @BindView(R.id.rv_kbis_agenda)
    RecyclerView rvKbisAgenda;
    private MeetingBean mMeetingBean;
    private List<MeetingBean.AgendaListBean> mMeetingAdapterBean;
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
        Map<String, Object> stringMap = IdeaApi.getSign();

        IdeaApi.getRequestLogin(stringMap);
        IdeaApi.getApiService()
                .getMeeting(stringMap)
                .compose(KbisAgendaFragment.this.<BasicResponse<MeetingBean>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse<MeetingBean>>(getActivity(), true) {
                    @Override
                    public void onSuccess(BasicResponse<MeetingBean> response) {
                        mMeetingBean = response.getData();

                        String agendaType = "";
                        switch (mMeetingBean.getAgendaList().get(0).getAgendaType()) {
                            case -1:
                                agendaType = "过期议程";
                                break;
                            case 0:
                                agendaType = "未来议程";
                                break;
                            case 1:
                                agendaType = " 明日议程";
                                break;
                            case 2:
                                agendaType = "下一议程";
                                break;
                            case 3:
                                agendaType = "当前议程";
                                break;
                        }

                        tvKbisPositionZeroAgendaType.setText(agendaType);
                        tvKbisPositionZeroAgendaTime.setText(mMeetingBean.getAgendaList().get(0).getTimeSlot());
                        tvKbisPositionZeroAgendaPosition.setText(mMeetingBean.getAgendaList().get(0).getPlace());
                        tvKbisPositionZeroAgendaName.setText(mMeetingBean.getAgendaList().get(0).getTitle());

                        mMeetingAdapterBean.clear();
                        mMeetingAdapterBean.addAll(mMeetingBean.getAgendaList());
                        mMeetingAdapterBean.remove(0);

                        mKbisAgendadapter = new KbisAgendadapter(mMeetingAdapterBean);
                        rvKbisAgenda.setAdapter(mKbisAgendadapter);
                    }
                });
    }
}
