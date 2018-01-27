package com.mengyang.kohler.account.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.gyf.barlibrary.ImmersionBar;
import com.mengyang.kohler.BaseActivity;
import com.mengyang.kohler.R;
import com.mengyang.kohler.common.view.CircleImageView;
import com.mengyang.kohler.common.view.TopView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserMsgEditActivity extends BaseActivity {

    @BindView(R.id.tv_user_msg_edit_top)
    TopView tvUserMsgEditTop;
    @BindView(R.id.civ_user_msg_edit_title)
    CircleImageView civUserMsgEditTitle;
    @BindView(R.id.et_user_msg_edit_name)
    EditText etUserMsgEditName;
    @BindView(R.id.bt_user_msg_edit_done)
    Button btUserMsgEditDone;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_msg_edit;
    }

    @Override
    protected void initValues() {
        //防止状态栏和标题重叠
        ImmersionBar.setTitleBar(this, tvUserMsgEditTop);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.civ_user_msg_edit_title, R.id.bt_user_msg_edit_done})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.civ_user_msg_edit_title:
                break;
            case R.id.bt_user_msg_edit_done:
                break;
        }
    }
}
