package com.mengyang.kohler.home.fragment;

import android.widget.EditText;

import com.gyf.barlibrary.ImmersionBar;
import com.mengyang.kohler.BaseFragment;
import com.mengyang.kohler.R;
import com.mengyang.kohler.common.utils.ToastUtil;
import com.mengyang.kohler.common.view.TopView;
import com.ryane.banner_lib.AdPageInfo;
import com.ryane.banner_lib.AdPlayBanner;
import com.ryane.banner_lib.transformer.FadeInFadeOutTransformer;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.ryane.banner_lib.AdPlayBanner.ImageLoaderType.GLIDE;
import static com.ryane.banner_lib.AdPlayBanner.IndicatorType.POINT_INDICATOR;

public class HomeFragment extends BaseFragment {

    @BindView(R.id.tv_home_top)
    TopView tvHomeTop;
    @BindView(R.id.et_home_search)
    EditText etHomeSearch;
    @BindView(R.id.ab_home_loop)
    AdPlayBanner abHomeLoop;
    //轮播图集合
    private List<AdPageInfo> mDatas = new ArrayList<>();

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
        AdPageInfo info1 = new AdPageInfo("拜仁球场冠绝全球", "http://onq81n53u.bkt.clouddn.com/photo1.jpg", "http://www.bairen.com", 1);
        AdPageInfo info2 = new AdPageInfo("日落东单一起战斗", "http://onq81n53u.bkt.clouddn.com/photo2.jpg", "http://www.riluodongdan.com", 4);
        AdPageInfo info3 = new AdPageInfo("香港夜景流连忘返", "http://onq81n53u.bkt.clouddn.com/photo3333.jpg", "http://www.hongkong.com", 2);
        AdPageInfo info4 = new AdPageInfo("耐克大法绝顶天下", "http://7xrwkh.com1.z0.glb.clouddn.com/1.jpg", "http://www.nike.com", 3);

        mDatas.add(info1);
        mDatas.add(info2);
        mDatas.add(info3);
        mDatas.add(info4);

        abHomeLoop.setImageLoadType(GLIDE);
        abHomeLoop.setOnPageClickListener(new AdPlayBanner.OnPageClickListener() {
            @Override
            public void onPageClick(AdPageInfo info, int postion) {
                ToastUtil.showToast("你点击了图片 " + info.getTitle() + "\n 跳转链接为：" + info.getClickUlr() + "\n 当前位置是：" + postion + "\n 当前优先级是：" + info.getOrder());
            }
        });
        //自动滚动
        abHomeLoop.setAutoPlay(true);
        //页码指示器
        abHomeLoop.setIndicatorType(POINT_INDICATOR);
        //normalColor为数字没选中时的背景颜色，selectedColor为数字选中时的背景颜色，numberColor为数字的字体颜色
        abHomeLoop.setNumberViewColor(0xcc00A600, 0xccea0000, 0xffffffff);
        //间隔时间
        abHomeLoop.setInterval(3000);
        //标题
        //abLoop.addTitleView(new TitleView(getContext()).setPosition(PARENT_TOP).setTitlePadding(5, 5, 5, 5).setTitleMargin(0, 0, 0, 25).setTitleSize(16).setViewBackground(0x55000000).setTitleColor(ContextCompat.getColor(getContext(), R.color.white)));
        //背景
        abHomeLoop.setBannerBackground(0xfffbf4f3);
        //切换动画
        abHomeLoop.setPageTransfromer(new FadeInFadeOutTransformer());
        //数据源
        abHomeLoop.setInfoList((ArrayList<AdPageInfo>) mDatas);
        abHomeLoop.setUp();
    }

    @Override
    protected void initData() {
    }
}
