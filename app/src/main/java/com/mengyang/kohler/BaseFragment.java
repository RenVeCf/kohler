package com.mengyang.kohler;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trello.rxlifecycle2.components.support.RxFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by zhpan on 2017/4/22.
 */

public abstract class BaseFragment extends RxFragment {

    protected View rootView;
    Unbinder unbinder;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(this.getLayoutId(), container, false);
            unbinder = ButterKnife.bind(this,rootView);
        }
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //初始化本地数据
        initValues();
        //初始化布局监听器
        initListener();
        //初始化网络数据
        initData();
    }

    /**
     * @return 返回xml布局 R.layout.xxx  布局文件
     */
    protected abstract int getLayoutId();

    /**
     * 初始化本地数据 比如List等 new出来
     */
    protected abstract void initValues();

    /**
     * 初始化布局监听器
     */
    protected abstract void initListener();

    /**
     * 初始话网络数据
     */
    protected abstract void initData();

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }
}
