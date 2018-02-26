package com.kohler.arscan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import com.kohler.arscan.adapter.SearchAdapter;
import com.kohler.arscan.bean.SearchItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchActivity extends AppCompatActivity {

    private final static String TAG = SearchActivity.class.getSimpleName();

    @BindView(R2.id.iv_top_scan)
    ImageView iv_top_scan;
    @BindView(R2.id.gv_search)
    GridView gv_search;

    private List<SearchItem> itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        initData();
        initView();
    }

    private void initData() {
        itemList = new ArrayList<>();
        SearchItem item1 = new SearchItem(R.drawable.yushi, "浴室家具");
        SearchItem item2 = new SearchItem(R.drawable.yiti, "一体超感坐便器");
        SearchItem item3 = new SearchItem(R.drawable.biangai, "智能便盖");
        SearchItem item4 = new SearchItem(R.drawable.jinggui, "镜柜");
        SearchItem item5 = new SearchItem(R.drawable.taipen, "台盆");
        SearchItem item6 = new SearchItem(R.drawable.zuobian, "坐便器");
        SearchItem item7 = new SearchItem(R.drawable.longtou, "龙头");
        SearchItem item8 = new SearchItem(R.drawable.shangye, "商业系列");
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
        iv_top_scan.setVisibility(View.GONE);

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
}
