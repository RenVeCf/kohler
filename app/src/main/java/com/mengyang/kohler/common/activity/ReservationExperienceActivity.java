package com.mengyang.kohler.common.activity;

import android.app.Dialog;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bigkoo.pickerview.adapter.ArrayWheelAdapter;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.contrarywind.listener.OnItemSelectedListener;
import com.contrarywind.view.WheelView;
import com.gyf.barlibrary.ImmersionBar;
import com.mengyang.kohler.App;
import com.mengyang.kohler.BaseActivity;
import com.mengyang.kohler.R;
import com.mengyang.kohler.common.net.IdeaApiService;
import com.mengyang.kohler.common.utils.DateUtils;
import com.mengyang.kohler.common.utils.JsonUtils;
import com.mengyang.kohler.common.utils.ToastUtil;
import com.mengyang.kohler.common.utils.VerifyUtils;
import com.mengyang.kohler.common.view.TopView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 科勒预约体验活动
 */

public class ReservationExperienceActivity extends BaseActivity {

    @BindView(R.id.tv_reservation_experience_top)
    TopView tvReservationExperienceTop;
    @BindView(R.id.iv_top_back)
    ImageView ivTopBack;
    @BindView(R.id.tv_reservation_experience_product_big)
    TextView tvReservationExperienceProductBig;
    @BindView(R.id.tv_reservation_experience_product_medium)
    TextView tvReservationExperienceProductMedium;
    @BindView(R.id.tv_reservation_experience_product_small)
    TextView tvReservationExperienceProductSmall;
    @BindView(R.id.tv_reservation_experience_province)
    TextView tvReservationExperienceProvince;
    @BindView(R.id.tv_reservation_experience_city)
    TextView tvReservationExperienceCity;
    @BindView(R.id.tv_reservation_experience_addr_key)
    TextView tvReservationExperienceAddrKey;
    @BindView(R.id.tv_reservation_experience_in_store_time)
    TextView tvReservationExperienceInStoreTime;
    @BindView(R.id.et_reservation_experience_phone_num)
    EditText etReservationExperiencePhoneNum;
    @BindView(R.id.bt_reservation_experience_commit)
    Button btReservationExperienceCommit;
    private TextView tvCenterTitle;

    private String url = "http://www.kohler.com.cn/chinaweb/book/add.action";
    private TimePickerView pvTime;
    List<String> mProvinceList = new ArrayList<>();
    List<String> mBathroomList = new ArrayList<>();
    Map<String, String> mSecondMap = new HashMap<>();
    Map<String, String> mThirdMap = new HashMap<>();
    Map<String, String> mCityMap = new HashMap<>();
    Map<String, String> mRoomNameMap = new HashMap<>();
    Map<String, String> mAddressMap = new HashMap<>();

    private Dialog dialog_one;
    private List<String> mOptionsItems = new ArrayList<>();
    private TextView tvSure;
    private TextView tvCancel;
    private String mSelectedText = "";
    private String mSelectedProvince = "";
    private String mSelectedCity = "";
    private String mSelectedRoomName = "";

    private String mSelectedType = "";
    private String mSelectedSecond = "";
    private String mSelectedThird = "";

    private boolean mIsShowDeatilAddress;
    private String mJson;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_reservation_experience;
    }

    @Override
    protected void initValues() {
        App.getManager().addActivity(this);
        //沉浸式状态栏初始化白色
        ImmersionBar.with(this).fitsSystemWindows(false).statusBarDarkFont(false).init();
        //防止状态栏和标题重叠
        ImmersionBar.setTitleBar(this, tvReservationExperienceTop);
        ivTopBack.setImageResource(R.mipmap.fanhuibai);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        initJsonData();
        parseAssetsJson();

        requestAddressData();
    }

    private void showRoomName(boolean isShowDia) {
        List<String> List2 = new ArrayList<>();
        for (Map.Entry<String, String> entry : mRoomNameMap.entrySet()) {
            if (entry.getKey().contains(mSelectedCity)) {
                String value = entry.getValue();
                if (!List2.contains(value)) {
                    List2.add(value);
                }
            }
        }

        selectOne(tvReservationExperienceAddrKey, List2, 3, "门店");
    }

    private void showCity(boolean isShowDia) {
        List<String> List = new ArrayList<>();
        for (Map.Entry<String, String> entry : mCityMap.entrySet()) {
            if (entry.getKey().contains(mSelectedProvince)) {
                String value = entry.getValue();
                if (!List.contains(value)) {
                    List.add(value);
                }
            }
        }

        selectOne(tvReservationExperienceCity, List, 2, "市");
    }

    private void showThird(boolean isShowDia) {
        List<String> List4 = new ArrayList<>();
        for (Map.Entry<String, String> entry : mThirdMap.entrySet()) {
            if (entry.getKey().contains(mSelectedSecond)) {
                String value = entry.getValue();
                if (!List4.contains(value)) {
                    List4.add(value);
                }
            }
        }

        selectOne(tvReservationExperienceProductSmall, List4, 6, "三级分类");
    }

    private void showSecond(boolean isShowDia) {
        List<String> List3 = new ArrayList<>();
        for (Map.Entry<String, String> entry : mSecondMap.entrySet()) {
            if (entry.getKey().contains(mSelectedType)) {
                String value = entry.getValue();
                if (!List3.contains(value)) {
                    List3.add(value);
                }
            }
        }

        selectOne(tvReservationExperienceProductMedium, List3, 5, "二级分类");
    }

    /**
     * 单选选择器样式
     *
     * @param view
     * @param item
     */
    private void selectOne(final TextView view, final List<String> item, final int type, String content) {
        if (item == null || item.size() < 1) {
            return;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(ReservationExperienceActivity.this);
        LayoutInflater inflater1 = LayoutInflater.from(ReservationExperienceActivity.this);
        View v = inflater1.inflate(R.layout.appointment_one, null);
        tvSure = v.findViewById(R.id.tv_sure);
        tvCancel = v.findViewById(R.id.tv_cancel);
        tvCenterTitle = v.findViewById(R.id.tv_center_title);
        tvCenterTitle.setText(content);
        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSelectedText = item.get(0);
                view.setText(mSelectedText);
                dialog_one.dismiss();

                handleClickEvent(type);
            }
        });
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_one.dismiss();
            }
        });
        WheelView wheelView = v.findViewById(R.id.wv_appointment_one);

        wheelView.setCyclic(false);

        wheelView.setAdapter(new ArrayWheelAdapter(item));
        wheelView.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(final int index) {
                tvSure.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mSelectedText = item.get(index);
                        view.setText(mSelectedText);
                        dialog_one.dismiss();

                        handleClickEvent(type);
                    }
                });
                tvCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog_one.dismiss();
                    }
                });
            }
        });

        dialog_one = builder.create();
        dialog_one.setCanceledOnTouchOutside(false);
        dialog_one.show();
        dialog_one.getWindow().setContentView(v);
        dialog_one.getWindow().setGravity(Gravity.CENTER);//可以设置显示的位置

        switch (view.getId()) {
            case R.id.tv_reservation_experience_addr_key:
                tvReservationExperienceAddrKey.setSingleLine();
                tvReservationExperienceAddrKey.setEllipsize(TextUtils.TruncateAt.valueOf("END"));
                break;
            case R.id.tv_reservation_experience_product_big:
                tvReservationExperienceProductBig.setSingleLine();
                tvReservationExperienceProductBig.setEllipsize(TextUtils.TruncateAt.valueOf("END"));
                break;
            case R.id.tv_reservation_experience_product_medium:
                tvReservationExperienceProductMedium.setSingleLine();
                tvReservationExperienceProductMedium.setEllipsize(TextUtils.TruncateAt.valueOf("END"));
                break;
            case R.id.tv_reservation_experience_product_small:
                tvReservationExperienceProductSmall.setSingleLine();
                tvReservationExperienceProductSmall.setEllipsize(TextUtils.TruncateAt.valueOf("END"));
                break;
        }
    }

    private void handleClickEvent(int type) {
        if (type == 1) {
            mSelectedProvince = mSelectedText;
        } else if (type == 2) {
            mSelectedCity = mSelectedText;
        } else if (type == 3) {
            mSelectedRoomName = mSelectedText;
        } else if (type == 4) {
            mSelectedType = mSelectedText;
        } else if (type == 5) {
            mSelectedSecond = mSelectedText;
        } else if (type == 6) {
            mSelectedThird = mSelectedText;
        }

        if (mIsShowDeatilAddress) {
            mIsShowDeatilAddress = false;
            List<String> List2 = new ArrayList<>();
            for (Map.Entry<String, String> entry : mAddressMap.entrySet()) {
                if (entry.getKey().contains(mSelectedRoomName)) {
                    String value = entry.getValue();
                    if (!List2.contains(value)) {
                        List2.add(value);
                    }
                }
            }

            //            if (List2.size() > 0) {
            //                tvAppointmentAddress.setText(List2.get(0));
            //            }
        }
    }

    @OnClick({R.id.tv_reservation_experience_product_big, R.id.tv_reservation_experience_product_medium, R.id.tv_reservation_experience_product_small, R.id.tv_reservation_experience_province, R.id.tv_reservation_experience_city, R.id.tv_reservation_experience_addr_key, R.id.tv_reservation_experience_in_store_time, R.id.et_reservation_experience_phone_num, R.id.bt_reservation_experience_commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_reservation_experience_product_big:
                selectOne(tvReservationExperienceProductBig, mBathroomList, 4, "一级分类");

                tvReservationExperienceProductMedium.setText("请选择");
                tvReservationExperienceProductSmall.setText("请选择");
                break;
            case R.id.tv_reservation_experience_product_medium:
                showSecond(true);

                tvReservationExperienceProductSmall.setText("请选择");
                break;
            case R.id.tv_reservation_experience_product_small:
                showThird(true);
                break;
            case R.id.tv_reservation_experience_province:
                selectOne(tvReservationExperienceProvince, mProvinceList, 1, "省");

                tvReservationExperienceCity.setText("请选择");
                tvReservationExperienceAddrKey.setText("请选择");
                break;
            case R.id.tv_reservation_experience_city:
                showCity(true);

                tvReservationExperienceAddrKey.setText("请选择");
                break;
            case R.id.tv_reservation_experience_addr_key:
                mIsShowDeatilAddress = true;
                showRoomName(true);
                break;
            case R.id.tv_reservation_experience_in_store_time:
                selectTime();
                break;
            case R.id.bt_reservation_experience_commit:
                appointmentCommit();
                break;
        }
    }

    private void requestAddressData() {
        OkHttpClient okHttpClient = new OkHttpClient();
        String url2 = "http://www.kohler.com.cn/js/exports_2.json";
        Request request = new Request.Builder().url(url2).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();

                try {
                    JSONArray jsonArray = new JSONArray(string);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        String province = jsonArray.getJSONObject(i).getString("province");
                        if (!mProvinceList.contains(province)) {
                            mProvinceList.add(province);
                        }

                        String city = jsonArray.getJSONObject(i).getString("city");
                        String roomname = jsonArray.getJSONObject(i).getString("roomname");
                        String address = jsonArray.getJSONObject(i).getString("address");

                        if (mCityMap.containsKey(province)) {
                            mCityMap.put(province + i, city);
                        } else {
                            mCityMap.put(province, city);
                        }

                        if (mRoomNameMap.containsKey(city)) {
                            mRoomNameMap.put(city + i, roomname);
                        } else {
                            mRoomNameMap.put(city, roomname);
                        }

                        if (mAddressMap.containsKey(roomname)) {
                            mAddressMap.put(roomname + i, address);
                        } else {
                            mAddressMap.put(roomname, address);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void parseAssetsJson() {
        try {
            JSONArray jsonArray = new JSONArray(mJson);
            for (int i = 0; i < jsonArray.length(); i++) {
                String type = jsonArray.getJSONObject(i).getString("type");
                String second = jsonArray.getJSONObject(i).getString("second");
                String third = jsonArray.getJSONObject(i).getString("third");

                if (!mBathroomList.contains(type)) {
                    mBathroomList.add(type);
                }

                if (mSecondMap.containsKey(type)) {
                    mSecondMap.put(type + i, second);
                } else {
                    mSecondMap.put(type, second);
                }

                if (mThirdMap.containsKey(second)) {
                    mThirdMap.put(second + i, third);
                } else {
                    mThirdMap.put(second, third);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 时间选择器样式
     */
    private void selectTime() {
        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        //startDate.set(2013,1,1);
        Calendar endDate = Calendar.getInstance();
        //endDate.set(2020,1,1);

        //正确设置方式 原因：注意事项有说明
        startDate.set(2013, 0, 1);
        endDate.set(2020, 11, 31);

        pvTime = new TimePickerBuilder(ReservationExperienceActivity.this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                tvReservationExperienceInStoreTime.setText(DateUtils.timedate(date.getTime() + ""));
            }
        })
                .setType(new boolean[]{true, true, true, true, false, false})// 默认全部显示
                .setCancelText("取消")//取消按钮文字
                .setSubmitText("确定")//确认按钮文字
                //                .setContentSize(18)//滚轮文字大小
                .setTitleSize(18)//标题文字大小
                .setTitleText("时间")
                .setOutSideCancelable(false)//点击屏幕，点在控件外部范围时，是否取消显示
                .isCyclic(false)//是否循环滚动
                .setTitleColor(Color.BLACK)//标题文字颜色
                .setSubmitColor(Color.BLACK)//确定按钮文字颜色
                .setCancelColor(Color.GRAY)//取消按钮文字颜色
                .setTitleBgColor(0xFFFFFFFF)//标题背景颜色 Night mode
                .setBgColor(0xFFFFFFFF)//滚轮背景颜色 Night mode
                .setDate(selectedDate)// 如果不设置的话，默认是系统时间*/
                .setRangDate(startDate, endDate)//起始终止年月日设定
                .setLabel("年", "月", "日", "时", "分", "秒")//默认设置为年月日时分秒
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .isDialog(true)//是否显示为对话框样式
                .build();
        pvTime.show();
    }

    private void appointmentCommit() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(IdeaApiService.DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS).writeTimeout(IdeaApiService.DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS).readTimeout(IdeaApiService.DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS).build();
        //提交信息不能为空
        if (tvReservationExperienceProvince.getText().toString().trim().equals("请选择") || tvReservationExperienceCity.getText().toString().trim().equals("请选择") || tvReservationExperienceAddrKey.getText().toString().trim().equals("请选择") || tvReservationExperienceProductBig.getText().toString().trim().equals("请选择") || tvReservationExperienceProductMedium.getText().toString().trim().equals("请选择") || tvReservationExperienceProductSmall.getText().toString().trim().equals("请选择") || tvReservationExperienceInStoreTime.getText().toString().trim().equals("请选择") || etReservationExperiencePhoneNum.getText().toString().trim().equals("")) {
            ToastUtil.showToast("请将信息填写完整!");
        } else if (VerifyUtils.isMobileNumber(etReservationExperiencePhoneNum.getText().toString().trim()) == false) {
            ToastUtil.showToast("请填写正确的电话号码!");
        } else {
            MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");
            HashMap<String, String> map = new HashMap<>();
            map.put("province", tvReservationExperienceProvince.getText().toString().trim());
            map.put("city", tvReservationExperienceCity.getText().toString().trim());
            map.put("addr_key", tvReservationExperienceAddrKey.getText().toString().trim());
            map.put("goods1", tvReservationExperienceProductBig.getText().toString().trim());
            map.put("goods2", tvReservationExperienceProductMedium.getText().toString().trim());
            map.put("goods3", tvReservationExperienceProductSmall.getText().toString().trim());
            map.put("time", tvReservationExperienceInStoreTime.getText().toString().trim());
            map.put("telephone", etReservationExperiencePhoneNum.getText().toString().trim());
            map.put("source", "pc");
            RequestBody requestBody = RequestBody.create(MEDIA_TYPE_JSON, JsonUtils.toJson(map));
            Request requestPost = new Request.Builder().url(url).post(requestBody).build();
            Call call = okHttpClient.newCall(requestPost);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    ReservationExperienceActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtil.showToast("预约成功！");
                        }
                    });
                }
            });
        }
    }

    //读取本地json生成json字符串
    private String initJsonData() {
        try {
            InputStream is = getResources().getAssets().open("goods.json");
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            mJson = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mJson;
    }
}
