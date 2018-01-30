package com.mengyang.kohler.account.activity;

import android.widget.Button;
import android.widget.EditText;

import com.gyf.barlibrary.ImmersionBar;
import com.mengyang.kohler.BaseActivity;
import com.mengyang.kohler.R;
import com.mengyang.kohler.common.view.TopView;

import butterknife.BindView;
import butterknife.OnClick;

public class ModifyPwdActivity extends BaseActivity {

    @BindView(R.id.tv_modify_pwd_top)
    TopView tvModifyPwdTop;
    @BindView(R.id.et_modify_pwd_old_pwd)
    EditText etModifyPwdOldPwd;
    @BindView(R.id.et_modify_pwd_new_pwd)
    EditText etModifyPwdNewPwd;
    @BindView(R.id.bt_modify_pwd_determine)
    Button btModifyPwdDetermine;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_modify_pwd;
    }

    @Override
    protected void initValues() {
        //防止状态栏和标题重叠
        ImmersionBar.setTitleBar(this, tvModifyPwdTop);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }

    @OnClick(R.id.bt_modify_pwd_determine)
    public void onViewClicked() {
    }
}
