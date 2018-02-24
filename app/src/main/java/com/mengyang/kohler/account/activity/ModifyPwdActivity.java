package com.mengyang.kohler.account.activity;

import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.gyf.barlibrary.ImmersionBar;
import com.mengyang.kohler.App;
import com.mengyang.kohler.BaseActivity;
import com.mengyang.kohler.R;
import com.mengyang.kohler.common.net.DefaultObserver;
import com.mengyang.kohler.common.net.IdeaApi;
import com.mengyang.kohler.common.view.TopView;
import com.mengyang.kohler.module.BasicResponse;

import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 修改密码
 */

public class ModifyPwdActivity extends BaseActivity {

    @BindView(R.id.tv_modify_pwd_top)
    TopView tvModifyPwdTop;
    @BindView(R.id.et_modify_pwd_old_pwd)
    EditText etModifyPwdOldPwd;
    @BindView(R.id.et_modify_pwd_new_pwd)
    EditText etModifyPwdNewPwd;
    @BindView(R.id.et_modify_pwd_new_pwd_again)
    EditText etModifyPwdNewPwdAgain;
    @BindView(R.id.bt_modify_pwd_determine)
    Button btModifyPwdDetermine;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_modify_pwd;
    }

    @Override
    protected void initValues() {
        App.getManager().addActivity(this);
        //防止状态栏和标题重叠
        ImmersionBar.setTitleBar(this, tvModifyPwdTop);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }

    private void getModifyPwd() {
        Map<String, String> stringMap = IdeaApi.getSign();
        stringMap.put("pwd", etModifyPwdOldPwd.getText().toString().trim());//原密码
        stringMap.put("newPwd", etModifyPwdNewPwd.getText().toString().trim());//新密码
        stringMap.put("newAgainPwd", etModifyPwdNewPwdAgain.getText().toString().trim());//重新输入新密码

        IdeaApi.getRequestLogin(stringMap);
        IdeaApi.getApiService()
                .getModifyPwd(stringMap)
                .compose(ModifyPwdActivity.this.<BasicResponse>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse>(ModifyPwdActivity.this, true) {
                    @Override
                    public void onSuccess(BasicResponse response) {
                        finish();
                    }
                });
    }

    @OnClick(R.id.bt_modify_pwd_determine)
    public void onViewClicked() {
        if (!etModifyPwdOldPwd.getText().toString().trim().equals("") && !etModifyPwdNewPwd.getText().toString().trim().equals("") && !etModifyPwdNewPwdAgain.getText().toString().trim().equals("") && etModifyPwdNewPwd.getText().toString().trim().equals(etModifyPwdNewPwdAgain.getText().toString().trim()))
            getModifyPwd();
    }
}
