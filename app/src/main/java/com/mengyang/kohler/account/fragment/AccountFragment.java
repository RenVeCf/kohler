package com.mengyang.kohler.account.fragment;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gyf.barlibrary.ImmersionBar;
import com.mengyang.kohler.App;
import com.mengyang.kohler.BaseFragment;
import com.mengyang.kohler.R;
import com.mengyang.kohler.account.activity.AccountMineLikeActivity;
import com.mengyang.kohler.account.activity.AccountMineReservationQueryActivity;
import com.mengyang.kohler.account.activity.AccountSettingsActivity;
import com.mengyang.kohler.account.adapter.FootPrintAdapter;
import com.mengyang.kohler.common.net.DefaultObserver;
import com.mengyang.kohler.common.net.IConstants;
import com.mengyang.kohler.common.net.IdeaApi;
import com.mengyang.kohler.common.utils.SPUtil;
import com.mengyang.kohler.common.view.CircleImageView;
import com.mengyang.kohler.common.view.SpacesItemDecoration;
import com.mengyang.kohler.common.view.TopView;
import com.mengyang.kohler.module.BasicResponse;
import com.mengyang.kohler.module.bean.FootPrintBean;
import com.mengyang.kohler.module.bean.UploadHeadPortraitBean;
import com.mengyang.kohler.whole_category.activity.CommodityDetailsActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * 账户
 */

public class AccountFragment extends BaseFragment {

    @BindView(R.id.tv_account_top)
    TopView tvAccountTop;
    @BindView(R.id.iv_account_settings)
    ImageView ivAccountSettings;
    @BindView(R.id.civ_account_title)
    CircleImageView civAccountTitle;
    @BindView(R.id.tv_account_name)
    TextView tvAccountName;
    @BindView(R.id.bt_account_like)
    Button btAccountLike;
    @BindView(R.id.bt_account_query)
    Button btAineQuery;
    @BindView(R.id.tv_account_browsing_no_login)
    TextView tvAccountBrowsingNoLogin;
    @BindView(R.id.rv_account_browsing)
    RecyclerView rvAccountBrowsing;
    //调用系统相册-选择图片数量
    private static final int IMAGE_NUM = 1;
    private PopupWindow mAccountTitleImgPopupWindow;
    private View mPopImgLayout;
    private PopupWindow mAccountTitleNamePopupWindow;
    private View mPopNameLayout;
    private ImageView ivAccountModifyTitleImg;
    private Button btAccountModifyTitleImgConfirm; //头像PopupWindow确定键
    private EditText etAccountPopNewName; ////名称PopupWindow输入框
    private Button btAccountPopNewName; //名称PopupWindow确定键
    private List<FootPrintBean> mFootPrintBean;
    private FootPrintAdapter mFootPrintAdapter;
    private int pageNum = 0;
    //是否登录
    private boolean is_Login = (boolean) SPUtil.get(App.getContext(), IConstants.IS_LOGIN, false);

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_account;
    }

    @Override
    protected void initValues() {
        //防止状态栏和标题重叠
        ImmersionBar.setTitleBar(getActivity(), tvAccountTop);

        LinearLayoutManager layoutManager = new LinearLayoutManager(App.getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvAccountBrowsing.setLayoutManager(layoutManager);
        rvAccountBrowsing.addItemDecoration(new SpacesItemDecoration(13));
        // 如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        rvAccountBrowsing.setHasFixedSize(true);
        rvAccountBrowsing.setItemAnimator(new DefaultItemAnimator());
        mFootPrintBean = new ArrayList<>();
        mFootPrintAdapter = new FootPrintAdapter(mFootPrintBean);
        rvAccountBrowsing.setAdapter(mFootPrintAdapter);

        if (is_Login) {
            final Drawable DrawableLeftLike = getResources().getDrawable(R.mipmap.shouchan);
            final Drawable DrawableLeftQuery = getResources().getDrawable(R.mipmap.yuyue);
            tvAccountBrowsingNoLogin.setVisibility(View.GONE);
            rvAccountBrowsing.setVisibility(View.VISIBLE);
            btAccountLike.setTextColor(Color.BLACK);
            btAineQuery.setTextColor(Color.BLACK);
            btAccountLike.setCompoundDrawablesWithIntrinsicBounds(DrawableLeftLike, null, null, null);
            btAineQuery.setCompoundDrawablesWithIntrinsicBounds(DrawableLeftQuery, null, null, null);
            btAccountLike.setBackgroundResource(R.drawable.account_stroke_lgoin_bt_bg);
            btAineQuery.setBackgroundResource(R.drawable.account_stroke_lgoin_bt_bg);
        } else {
            final Drawable DrawableLeftLike = getResources().getDrawable(R.mipmap.icon_collection);
            final Drawable DrawableLeftQuery = getResources().getDrawable(R.mipmap.icon_inquiry);
            tvAccountBrowsingNoLogin.setVisibility(View.VISIBLE);
            rvAccountBrowsing.setVisibility(View.GONE);
            civAccountTitle.setScaleType(ImageView.ScaleType.CENTER_CROP);
            civAccountTitle.setImageResource(R.mipmap.oval);
            tvAccountName.setText(App.getContext().getResources().getString(R.string.login_or_register));
            btAccountLike.setCompoundDrawablesWithIntrinsicBounds(DrawableLeftLike, null, null, null);
            btAineQuery.setCompoundDrawablesWithIntrinsicBounds(DrawableLeftQuery, null, null, null);
            btAccountLike.setTextColor(App.getContext().getResources().getColor(R.color.account_no_login_bt));
            btAineQuery.setTextColor(App.getContext().getResources().getColor(R.color.account_no_login_bt));
            btAccountLike.setBackgroundResource(R.drawable.account_stroke_no_lgoin_bt_bg);
            btAineQuery.setBackgroundResource(R.drawable.account_stroke_no_lgoin_bt_bg);
        }
        mAccountTitleImgPopupWindow = new PopupWindow(App.getContext());
        mAccountTitleImgPopupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        mAccountTitleImgPopupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        LayoutInflater inflater_img = LayoutInflater.from(App.getContext());
        mPopImgLayout = inflater_img.inflate(R.layout.popup_window_account_title_img, null);
        mAccountTitleImgPopupWindow.setContentView(mPopImgLayout);
        mAccountTitleImgPopupWindow.setBackgroundDrawable(new ColorDrawable(0x4c000000));
        mAccountTitleImgPopupWindow.setOutsideTouchable(false);
        mAccountTitleImgPopupWindow.setFocusable(true);
        ivAccountModifyTitleImg = mPopImgLayout.findViewById(R.id.iv_account_modify_title_img);
        btAccountModifyTitleImgConfirm = mPopImgLayout.findViewById(R.id.bt_account_modify_title_img_confirm);
        btAccountModifyTitleImgConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //更换头像
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, IMAGE_NUM);
                mAccountTitleImgPopupWindow.dismiss();
            }
        });

        mAccountTitleNamePopupWindow = new PopupWindow(App.getContext());
        mAccountTitleNamePopupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        mAccountTitleNamePopupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        LayoutInflater inflater_name = LayoutInflater.from(App.getContext());
        mPopNameLayout = inflater_name.inflate(R.layout.popup_window_account_title_name, null);
        mAccountTitleNamePopupWindow.setContentView(mPopNameLayout);
        mAccountTitleNamePopupWindow.setBackgroundDrawable(new ColorDrawable(0x4c000000));
        mAccountTitleNamePopupWindow.setOutsideTouchable(false);
        mAccountTitleNamePopupWindow.setFocusable(true);
        etAccountPopNewName = mPopNameLayout.findViewById(R.id.et_account_pop_new_name);
        btAccountPopNewName = mPopNameLayout.findViewById(R.id.bt_account_pop_new_name);
        btAccountPopNewName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //更换名称
                if (!etAccountPopNewName.getText().toString().trim().equals("")) {
                    getNikeName();
                }
            }
        });
    }

    public void getNikeName() {
        Map<String, String> stringMap = IdeaApi.getSign();
        stringMap.put("nickName", etAccountPopNewName.getText().toString().trim());//昵称

        IdeaApi.getRequestLogin(stringMap);
        IdeaApi.getApiService()
                .getModifyNikeName(stringMap)
                .compose(this.<BasicResponse>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse>(getActivity(), true) {
                    @Override
                    public void onSuccess(BasicResponse response) {
                        tvAccountName.setText(etAccountPopNewName.getText().toString().trim());
                        mAccountTitleNamePopupWindow.dismiss();
                    }
                });
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
//        Map<String, String> stringMap = IdeaApi.getSign();
//        stringMap.put("pageNum", pageNum + "");
//        stringMap.put("pageSize", 3 + "");
//
//        IdeaApi.getRequestLogin(stringMap);
//        IdeaApi.getApiService()
//                .getFootPrint(stringMap)
//                .compose(this.<BasicResponse<List<FootPrintBean>>>bindToLifecycle())
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new DefaultObserver<BasicResponse<List<FootPrintBean>>>(getActivity(), true) {
//                    @Override
//                    public void onSuccess(BasicResponse<List<FootPrintBean>> response) {
//                        if (response != null) {
//                            if (pageNum == 0) {
//                                mFootPrintBean.clear();
//                                mFootPrintBean.addAll(response.getData());
//                                if (mFootPrintBean.size() > 0) {
//                                    pageNum += 1;
//                                    mFootPrintAdapter = new FootPrintAdapter(mFootPrintBean);
//                                    rvAccountBrowsing.setAdapter(mFootPrintAdapter);
//                                    mFootPrintAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
//                                        @Override
//                                        public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
//                                            startActivity(new Intent(getActivity(), CommodityDetailsActivity.class).putExtra("CommodityDetails", mFootPrintBean.get(position).getResultList().get(position).getSkuCode()));
//                                        }
//                                    });
//                                } else {
//                                    mFootPrintAdapter.loadMoreEnd();
//                                }
//                            } else {
//                                if (response.getData().size() > 0) {
//                                    pageNum += 1;
//                                    mFootPrintBean.addAll(response.getData());
//                                    mFootPrintAdapter.addData(response.getData());
//                                    mFootPrintAdapter.loadMoreComplete(); //完成本次
//                                } else {
//                                    mFootPrintAdapter.loadMoreEnd(); //完成所有加载
//                                }
//                            }
//                        } else {
//                            mFootPrintAdapter.loadMoreEnd();
//                        }
//                    }
//                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //获取图片路径
        if (requestCode == IMAGE_NUM && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            String[] filePathColumns = {MediaStore.Images.Media.DATA};
            Cursor c = App.getContext().getContentResolver().query(selectedImage, filePathColumns, null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePathColumns[0]);
            String imagePath = c.getString(columnIndex);
            showImage(imagePath);
            c.close();
        }
    }

    //加载头像图片
    private void showImage(final String imaePath) {
        Map<String, String> stringMap = IdeaApi.getSign();
        stringMap.put("portraitUrl", imaePath); // 头像URL

        IdeaApi.getRequestLogin(stringMap);
        IdeaApi.getApiService()
                .getModifyHeadPortrait(stringMap)
                .compose(this.<BasicResponse>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse>(getActivity(), false) {
                    @Override
                    public void onSuccess(BasicResponse response) {
                        File file = new File(imaePath);
                        RequestBody imageBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                        MultipartBody.Builder builder = new MultipartBody.Builder()
                                .setType(MultipartBody.FORM);
                        for (String key : IdeaApi.getSign().keySet()) {
                            builder.addFormDataPart(key, IdeaApi.getSign().get(key));
                        }
                        builder.addFormDataPart("portraitUrl", file.getName(), imageBody);
                        List<MultipartBody.Part> parts = builder.build().parts();

                        IdeaApi.getApiService()
                                .getUploadHeadPortrait(parts)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new DefaultObserver<BasicResponse<UploadHeadPortraitBean>>(getActivity(), false) {
                                    @Override
                                    public void onSuccess(BasicResponse<UploadHeadPortraitBean> response) {
                                    }
                                });
                    }
                });

        Bitmap bm = BitmapFactory.decodeFile(imaePath);
        civAccountTitle.setImageBitmap(bm);
    }

    @OnClick({R.id.iv_account_settings, R.id.civ_account_title, R.id.tv_account_name, R.id.bt_account_like, R.id.bt_account_query})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_account_settings:
                startActivity(new Intent(App.getContext(), AccountSettingsActivity.class));
                break;
            case R.id.civ_account_title:
                if (is_Login) {
                    civAccountTitle.setDrawingCacheEnabled(true);
                    Bitmap obmp = Bitmap.createBitmap(civAccountTitle.getDrawingCache());
                    ivAccountModifyTitleImg.setImageBitmap(obmp);
                    civAccountTitle.setDrawingCacheEnabled(false);
                    mAccountTitleImgPopupWindow.showAsDropDown(view, 0, 0);
                } else {

                }
                break;
            case R.id.tv_account_name:
                if (is_Login) {
                    mAccountTitleNamePopupWindow.showAsDropDown(view, 0, 0);
                }
                break;
            case R.id.bt_account_like:
                if ((boolean) SPUtil.get(App.getContext(), IConstants.IS_LOGIN, false))
                    startActivity(new Intent(App.getContext(), AccountMineLikeActivity.class));
                break;
            case R.id.bt_account_query:
                if ((boolean) SPUtil.get(App.getContext(), IConstants.IS_LOGIN, false))
                    startActivity(new Intent(App.getContext(), AccountMineReservationQueryActivity.class));
                break;
        }
    }
}
