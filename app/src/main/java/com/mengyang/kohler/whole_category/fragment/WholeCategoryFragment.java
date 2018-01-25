package com.mengyang.kohler.whole_category.fragment;

import android.support.v7.widget.RecyclerView;
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

public class WholeCategoryFragment extends BaseFragment {

    @BindView(R.id.tv_whole_category_top)
    TopView tvWholeCategoryTop;
    @BindView(R.id.rv_whole_category)
    RecyclerView rvWholeCategory;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_whole_category;
    }

    @Override
    protected void initValues() {
        //防止状态栏和标题重叠
        ImmersionBar.setTitleBar(getActivity(), tvWholeCategoryTop);

        List<String> datas = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            datas.add(String.valueOf(i));
        }

        Config config = new Config();
        config.secondaryScale = 0.8f;
        config.scaleRatio = 0.4f;
        config.maxStackCount = 3;
        config.initialStackCount = 2;
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
