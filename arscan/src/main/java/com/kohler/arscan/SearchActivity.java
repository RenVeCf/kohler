package com.kohler.arscan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.kohler.arscan.adapter.SearchAdapter;
import com.kohler.arscan.bean.SearchItem;
import com.kohler.arscan.util.SPUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchActivity extends AppCompatActivity implements OnClickListener {

    private final static String TAG = SearchActivity.class.getSimpleName();

    @BindView(R2.id.gv_search)
    GridView gv_search;

    private List<SearchItem> itemList;
    private PopupWindow notebookPop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        initData();
        initView();
        initPop();
    }

    private void initPop() {
        View view = LayoutInflater.from(this).inflate(R.layout.notebook_pop, null);

        view.findViewById(R.id.tv_notebook_download).setOnClickListener(this);
        view.findViewById(R.id.iv_notebook_cancel).setOnClickListener(this);

        notebookPop = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    private void initData() {
        itemList = new ArrayList<>();
        SearchItem item1 = new SearchItem(R.drawable.yushi, "浴室家具");
        SearchItem item2 = new SearchItem(R.drawable.yiti, "一体超感座便器");
        SearchItem item3 = new SearchItem(R.drawable.biangai, getString(R.string.biangai));
        SearchItem item4 = new SearchItem(R.drawable.jinggui, "镜柜");
        SearchItem item5 = new SearchItem(R.drawable.taipen, "台盆");
        SearchItem item6 = new SearchItem(R.drawable.zuobian, "座便器");
        SearchItem item7 = new SearchItem(R.drawable.longtou, "龙头");
        SearchItem item8 = new SearchItem(R.drawable.shangyong, "商用系列");
        itemList.add(item1);
        itemList.add(item2);
        itemList.add(item3);
        itemList.add(item4);
        itemList.add(item5);
        itemList.add(item6);
        itemList.add(item7);
        itemList.add(item8);
    }

    private void initView() {
        SearchAdapter adapter = new SearchAdapter(this, itemList);
        gv_search.setAdapter(adapter);
        gv_search.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SearchActivity.this, PlayActivity.class);
                intent.putExtra("flag", position);
                startActivity(intent);
            }
        });
    }

    @OnClick(R2.id.iv_top_back)
    public void back() {
        finish();
    }

    @OnClick(R2.id.ll_main)
    public void main() {
        // TODO: 2018/3/2 注释解开 
        Intent intent = new Intent();
        if (((boolean) SPUtil.get(this, "isLogin", false))) {
            if (SPUtil.get(this, "no_type", "").equals("dealer")) {
                intent.setClassName("com.mengyang.kohler", "com.mengyang.kohler.home.activity.MeetingActivity");
                startActivity(intent);
            }
        } else {
            intent.setClassName("com.mengyang.kohler", "com.mengyang.kohler.account.activity.LoginActivity");
            startActivity(intent);
        }
    }

    @OnClick(R2.id.ll_notebook)
    public void notebook() {
        notebookPop.showAtLocation(gv_search, Gravity.NO_GRAVITY, 0, 0);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.iv_notebook_cancel) {
            notebookPop.dismiss();
        } else if (id == R.id.tv_notebook_download) {
            notebookPop.dismiss();

            // TODO: 2018/3/2 注释解开 
            Intent intent = new Intent();
            if (((boolean) SPUtil.get(this, "isLogin", false))) {
                if (SPUtil.get(this, "no_type", "").equals("dealer") || SPUtil.get(this, "no_type", "").equals("designer")) {
                    intent.setClassName("com.mengyang.kohler", "com.mengyang.kohler.home.activity.MineManualActivity");
                    startActivity(intent);
                }
            } else {
                intent.setClassName("com.mengyang.kohler", "com.mengyang.kohler.account.activity.LoginActivity");
                startActivity(intent);
            }
        }
    }
}
