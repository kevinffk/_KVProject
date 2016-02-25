package com.kv.iprojectdemo.pull2refresh;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.kv.iprojectdemo.R;
import com.kv.iprojectlib.plugin.pulltorefresh.XListView;
import com.kv.iprojectlib.utils.DateUtils;

public class Pull2RefreshAct extends Activity implements XListView.IXListViewListener {

    private XListView listView;
    
    private List<String> dataList = new ArrayList<String>();
    
    private ArrayAdapter<String> adapter;
    
    private int curItem = 0;
    
    private boolean isMoreLoad = false;
    
    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
            case 1:
                initList();
                break;
            case 2:
                loadMoreList();
                break;
            default:
                break;
            }
        }        
    };
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pull2refresh_test);
        
        
        listView = (XListView) findViewById(R.id.list);
        
        for (int i = 0; i < 15; i ++) {
            dataList.add("item" + i);
            curItem = i;
        }
        
        listView.setPullRefreshEnable(true);
        listView.setPullLoadEnable(true);
        listView.setAutoLoadEnable(true);
        listView.setXListViewListener(this);
        listView.setRefreshTime(DateUtils.getChinaTime());
        
        handler.sendEmptyMessageDelayed(1, 400);
    }
    
    private void onStopListTrack() {
        listView.stopRefresh();
        listView.stopLoadMore();
        listView.setRefreshTime(DateUtils.getChinaTime());
    }
    
    private void onDefineLoadMore() {
        if (adapter.getCount() >= 50) {
            Toast.makeText(this, "display finish", Toast.LENGTH_SHORT).show();
            listView.setPullLoadEnable(false);
        }
    }
    
    private void initList() {
        onStopListTrack();
        dataList.clear();
        
        for (int i = 0; i < 15; i ++) {
            dataList.add("item" + i);
            curItem = i;
        }
        listView.setPullLoadEnable(true);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dataList);
        listView.setAdapter(adapter);
        onDefineLoadMore();
    }

    private void loadMoreList() {
        isMoreLoad = false;
        int k = curItem + 1;
        if (k >= 44) {
            for (int i = k; i < k + 7; i++) {
                dataList.add("item" + i);
                curItem = i;
            }
        } else {
            for (int i = k; i < k +15; i++) {
                dataList.add("item" + i);
                curItem = i;
            }
        }
        adapter.notifyDataSetChanged();
        onDefineLoadMore();
    }
    
    @Override
    public void onRefresh() {
        handler.sendEmptyMessageDelayed(1, 400);
    }

    @Override
    public void onLoadMore() {
        if (!isMoreLoad) {
            isMoreLoad = true;
            handler.sendEmptyMessageDelayed(2, 400);
        }
    }
    
    

}
