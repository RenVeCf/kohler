package com.mengyang.kohler.whole_category.fragment;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.mengyang.kohler.BaseFragment;
import com.mengyang.kohler.R;
import com.mengyang.kohler.common.net.DefaultObserver;
import com.mengyang.kohler.common.net.IdeaApi;
import com.mengyang.kohler.common.view.TopView;
import com.mengyang.kohler.module.BasicResponse;
import com.mengyang.kohler.module.bean.NotSelectClassificationBean;
import com.mengyang.kohler.whole_category.adapter.StackAdapter;
import com.mengyang.kohler.whole_category.view.Align;
import com.mengyang.kohler.whole_category.view.Config;
import com.mengyang.kohler.whole_category.view.StackLayoutManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 全品类
 */

public class WholeCategoryFragment extends BaseFragment implements StackLayoutManager.changeListenning {

    @BindView(R.id.tv_whole_category_top)
    TopView tvWholeCategoryTop;
    @BindView(R.id.rv_whole_category)
    RecyclerView rvWholeCategory;
    @BindView(R.id.iv_top_customer_service)
    ImageView ivTopCustomerService;
    @BindView(R.id.iv_top_system_msg)
    ImageView ivTopSystemMsg;
    @BindView(R.id.tv_whole_category_visible)
    TextView tv_whole_category_visible;
    @BindView(R.id.tv_whole_category_gone)
    TextView tv_whole_category_gone;
    @BindView(R.id.tv_title_en)
    TextView mTvTitleEn;
    @BindView(R.id.iv_title)
    ImageView mIvTitle;

    private List<NotSelectClassificationBean> mNotSelectClassificationBean;
    private List<NotSelectClassificationBean> mNotSelectClassificationPositiveSequenceBean;
    private StackAdapter mStackAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_whole_category;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    protected void initValues() {
        //防止状态栏和标题重叠
        ImmersionBar.setTitleBar(getActivity(), tvWholeCategoryTop);

        //        if (SPUtil.get(App.getContext(), IConstants.TYPE, "").equals("dealer"))
        ivTopCustomerService.setVisibility(View.VISIBLE);
        //        else
        //            ivTopCustomerService.setVisibility(View.GONE);
        ivTopCustomerService.setImageResource(R.mipmap.kefubai);
        ivTopSystemMsg.setImageResource(R.mipmap.youxiangbai);

        mNotSelectClassificationBean = new ArrayList<>();
        mNotSelectClassificationPositiveSequenceBean = new ArrayList<>();
    }

    @Override
    protected void initListener() {
    }

    @Override
    protected void initData() {
        Map<String, String> stringMap = IdeaApi.getSign();

        IdeaApi.getRequestLogin(stringMap);
        IdeaApi.getApiService()
                .getNotSelectClassification(stringMap)
                .compose(this.<BasicResponse<List<NotSelectClassificationBean>>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse<List<NotSelectClassificationBean>>>(getActivity(), true) {
                    @Override
                    public void onSuccess(BasicResponse<List<NotSelectClassificationBean>> response) {
                        if (response != null) {
                            mNotSelectClassificationBean.clear();
                            mNotSelectClassificationBean = response.getData();

                            //StackLayoutManager 卡片堆叠框架的index是从右向左，所以遍历List转成从左向右使用
                            for (int i = mNotSelectClassificationBean.size() - 1; i >= 0; i--) {
                                if (!TextUtils.isEmpty(mNotSelectClassificationBean.get(i).getKvUrl())) {
                                    mNotSelectClassificationPositiveSequenceBean.add(mNotSelectClassificationBean.get(i));
                                }
                            }

                            Config config = new Config();
                            config.secondaryScale = 0f;
                            config.scaleRatio = 0.2f;//上一层堆叠与下层堆叠的 marginBottom
                            config.maxStackCount = 3;//边缘显示的堆叠层数
                            config.initialStackCount = mNotSelectClassificationPositiveSequenceBean.size() - 1;
                            config.space = getResources().getDimensionPixelOffset(R.dimen.item_space);//上一层图片与下一层的距离

                            config.align = Align.RIGHT;//堆叠显示的方向


                            StackLayoutManager stackLayoutManager = new StackLayoutManager(config);
                            stackLayoutManager.setChangeListenning(WholeCategoryFragment.this);

                            rvWholeCategory.setLayoutManager(stackLayoutManager);

                            mStackAdapter = new StackAdapter(mNotSelectClassificationPositiveSequenceBean);
                            rvWholeCategory.setAdapter(mStackAdapter);
                        }
                    }
                });
    }

    @Override
    public void changeListenning(int position) {
        if (position >= 1) {

        }

        if ((mNotSelectClassificationPositiveSequenceBean.size() - 1) == position) {
            mIvTitle.setVisibility(View.VISIBLE);
            mTvTitleEn.setVisibility(View.GONE);

            tv_whole_category_visible.setText("科勒精选");
            //            mTvTitleEn.setText(mNotSelectClassificationPositiveSequenceBean.get(position).getNameEn());
        } else {
            tv_whole_category_visible.setText(mNotSelectClassificationPositiveSequenceBean.get(position).getNameCn());
            mTvTitleEn.setText(mNotSelectClassificationPositiveSequenceBean.get(position).getNameEn());
            mIvTitle.setVisibility(View.GONE);
            mTvTitleEn.setVisibility(View.VISIBLE);
        }
    }
}
