package com.mengyang.kohler.home.fragment;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.gyf.barlibrary.ImmersionBar;
import com.mengyang.kohler.BaseFragment;
import com.mengyang.kohler.R;
import com.mengyang.kohler.common.utils.LogUtils;
import com.mengyang.kohler.common.view.TopView;
import com.mengyang.kohler.home.activity.HomeSearchActivity;
import com.mengyang.kohler.home.activity.MeetingActivity;
import com.mengyang.kohler.home.adapter.HomeBooksAdapter;
import com.mengyang.kohler.whole_category.activity.CommodityClassificationActivity;
import com.mengyang.kohler.whole_category.activity.CommodityDetailsActivity;
import com.ryane.banner.AdPageInfo;
import com.ryane.banner.AdPlayBanner;
import com.ryane.banner.transformer.FadeInFadeOutTransformer;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.ryane.banner.AdPlayBanner.ImageLoaderType.GLIDE;
import static com.ryane.banner.AdPlayBanner.IndicatorType.NONE_INDICATOR;

/**
 * 主页
 */

public class HomeFragment extends BaseFragment {

    @BindView(R.id.tv_home_top)
    TopView tvHomeTop;
    @BindView(R.id.et_home_search)
    EditText etHomeSearch;
    @BindView(R.id.ab_home_loop)
    AdPlayBanner abHomeLoop;
    @BindView(R.id.rv_home_video)
    RecyclerView rvHomeVideo;
    @BindView(R.id.rv_home_books)
    RecyclerView rvHomeBooks;
    //    @BindView(R.id.rv_home_editor_selection)
    //    RecyclerView rvHomeEditorSelection;
    //    @BindView(R.id.iv_home_map)
    //    ImageView ivHomeMap;
    @BindView(R.id.v_home_banner1)
    View vHomeBanner1;
    @BindView(R.id.v_home_banner2)
    View vHomeBanner2;
    @BindView(R.id.v_home_banner3)
    View vHomeBanner3;
    @BindView(R.id.iv_top_menu)
    ImageView ivTopMenu;
    @BindView(R.id.iv_home_search)
    ImageView ivHomeSearch;
    //轮播图集合
    private List<AdPageInfo> mDatas = new ArrayList<>();
    //我的图册
    private HomeBooksAdapter mHomeBooksAdapter;
    private OnFragmentInteractionListener mListener;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initValues() {
        //防止状态栏和标题重叠
        ImmersionBar.setTitleBar(getActivity(), tvHomeTop);
        //轮播
        abHomeLoop.measure(0, 0);
    }

    @Override
    protected void initListener() {
        if (mDatas.size() == 0) {
            AdPageInfo info1 = new AdPageInfo("拜仁球场冠绝全球", "http://onq81n53u.bkt.clouddn.com/photo1.jpg", "http://www.bairen.com", 1);
            AdPageInfo info2 = new AdPageInfo("日落东单一起战斗", "http://onq81n53u.bkt.clouddn.com/photo2.jpg", "http://www.riluodongdan.com", 4);
            AdPageInfo info3 = new AdPageInfo("香港夜景流连忘返", "http://onq81n53u.bkt.clouddn.com/photo3333.jpg", "http://www.hongkong.com", 2);
            AdPageInfo info4 = new AdPageInfo("耐克大法绝顶天下", "http://onq81n53u.bkt.clouddn.com/photo3.jpg", "http://www.nike.com", 3);

            mDatas.add(info1);
            mDatas.add(info2);
            mDatas.add(info3);
            mDatas.add(info4);
        }
        abHomeLoop.setImageLoadType(GLIDE);
        abHomeLoop.setOnPageClickListener(new AdPlayBanner.OnPageClickListener() {
            @Override
            public void onPageClick(AdPageInfo info, int postion) {
                if (postion == 0)
                    startActivity(new Intent(getActivity(), CommodityClassificationActivity.class));
                else if (postion == 1)
                    startActivity(new Intent(getActivity(), MeetingActivity.class));
                else
                    startActivity(new Intent(getActivity(), CommodityDetailsActivity.class));
                //                ToastUtil.showToast("你点击了图片 " + info.getTitle() + "\n 跳转链接为：" + info.getClickUlr() + "\n 当前位置是：" + postion + "\n 当前优先级是：" + info.getOrder());
            }
        });
        //自动滚动
        abHomeLoop.setAutoPlay(true);
        //页码指示器
        abHomeLoop.setIndicatorType(NONE_INDICATOR);
        //        //normalColor为数字没选中时的背景颜色，selectedColor为数字选中时的背景颜色，numberColor为数字的字体颜色
        //        abHomeLoop.setNumberViewColor(0xcc00A600, 0xccea0000, 0xffffffff);
        //间隔时间
        abHomeLoop.setInterval(3000);
        //标题
        //abLoop.addTitleView(new TitleView(getContext()).setPosition(PARENT_TOP).setTitlePadding(5, 5, 5, 5).setTitleMargin(0, 0, 0, 25).setTitleSize(16).setViewBackground(0x55000000).setTitleColor(ContextCompat.getColor(getContext(), R.color.white)));
        //背景
        abHomeLoop.setBannerBackground(0xffffffff);
//        //切换动画
//        abHomeLoop.setPageTransformer(new FadeInFadeOutTransformer());
        //数据源
        abHomeLoop.setInfoList((ArrayList<AdPageInfo>) mDatas);
        abHomeLoop.setUp();
    }

    @Override
    protected void initData() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(String data);
    }

    @OnClick({R.id.et_home_search, R.id.iv_top_menu, R.id.iv_home_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_home_search:
                startActivity(new Intent(getActivity(), HomeSearchActivity.class));
                break;
            case R.id.iv_top_menu:
                mListener.onFragmentInteraction("");
                break;
            case R.id.et_home_search:
                break;
            //            case R.id.iv_home_map:
            //                startActivity(new Intent(getActivity(), ShopsListActivity.class));
            //                break;
        }
    }
}
