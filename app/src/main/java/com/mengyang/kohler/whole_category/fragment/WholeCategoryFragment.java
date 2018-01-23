package com.mengyang.kohler.whole_category.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.gyf.barlibrary.ImmersionBar;
import com.mengyang.kohler.App;
import com.mengyang.kohler.BaseFragment;
import com.mengyang.kohler.R;
import com.mengyang.kohler.common.view.TopView;
import com.mengyang.kohler.whole_category.view.CardDataImpl;
import com.ramotion.expandingcollection.ECBackgroundSwitcherView;
import com.ramotion.expandingcollection.ECCardData;
import com.ramotion.expandingcollection.ECPagerView;
import com.ramotion.expandingcollection.ECPagerViewAdapter;

import java.util.List;

import butterknife.BindView;

public class WholeCategoryFragment extends BaseFragment {

    @BindView(R.id.tv_whole_category_top)
    TopView tvWholeCategoryTop;
    @BindView(R.id.ec_pager_element)
    ECPagerView ecPagerElement;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_whole_category;
    }

    @Override
    protected void initValues() {
        //防止状态栏和标题重叠
        ImmersionBar.setTitleBar(getActivity(), tvWholeCategoryTop);
    }

    @Override
    protected void initListener() {
        List<ECCardData> dataset = CardDataImpl.generateExampleData();
        ECPagerViewAdapter ecPagerViewAdapter = new ECPagerViewAdapter(App.getContext(), dataset) {

            @Override
            public void instantiateCard(LayoutInflater inflaterService, ViewGroup head, ListView list, ECCardData data) {
                list.setBackgroundResource(R.mipmap.ic_launcher_round);
                head.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                    }
                });
            }
        };
        ecPagerElement.setPagerViewAdapter(ecPagerViewAdapter);

        // Add background switcher to pager view
        ecPagerElement.setBackgroundSwitcherView((ECBackgroundSwitcherView) getActivity().findViewById(R.id.ec_bg_switcher_element));

        // Directly modifying dataset
        dataset.remove(2);
        ecPagerViewAdapter.notifyDataSetChanged();
    }

    // Card collapse on back pressed
//    @Override
//    public void onBackPressed() {
//        if (!ecPagerElement.collapse())
//            super.onBackPressed();
//    }

    @Override
    protected void initData() {

    }
}
