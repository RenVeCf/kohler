package com.mengyang.kohler.whole_category.fragment;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.gyf.barlibrary.ImmersionBar;
import com.mengyang.kohler.BaseFragment;
import com.mengyang.kohler.R;
import com.mengyang.kohler.common.view.TopView;
import com.mengyang.kohler.whole_category.view.Align;
import com.mengyang.kohler.whole_category.view.Config;
import com.mengyang.kohler.whole_category.view.StackAdapter;
import com.mengyang.kohler.whole_category.view.StackLayoutManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 全品类
 */

public class WholeCategoryFragment extends BaseFragment {

    @BindView(R.id.tv_whole_category_top)
    TopView tvWholeCategoryTop;
    @BindView(R.id.rv_whole_category)
    RecyclerView rvWholeCategory;
    @BindView(R.id.iv_top_customer_service)
    ImageView ivTopCustomerService;
    @BindView(R.id.iv_top_system_msg)
    ImageView ivTopSystemMsg;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_whole_category;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    protected void initValues() {
        //防止状态栏和标题重叠
        ImmersionBar.setTitleBar(getActivity(), tvWholeCategoryTop);

        ivTopCustomerService.setImageResource(R.mipmap.kefubai);
        ivTopSystemMsg.setImageResource(R.mipmap.youxiangbai);

        List<String> datas = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            datas.add(String.valueOf(i));
        }

        Config config = new Config();
        config.secondaryScale = 0f;
        config.scaleRatio = 0.4f;
        config.maxStackCount = 3;
        config.initialStackCount = 14;
        config.space = getResources().getDimensionPixelOffset(R.dimen.item_space);

        config.align = Align.RIGHT;
        rvWholeCategory.setLayoutManager(new StackLayoutManager(config));
        rvWholeCategory.setAdapter(new StackAdapter(datas));
    }

    @Override
    protected void initListener() {
    }

    @Override
    protected void initData() {

    }
}
