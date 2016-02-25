package com.kv.iprojectdemo.slideview;

import android.app.Activity;
import android.os.Bundle;

import com.kv.iprojectdemo.R;
import com.kv.iprojectlib.plugin.slidelistview.SwipeListView;

public class SlideViewTestAct extends Activity {

    private SwipeListView listView;
    private SlideAdapter adapter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slideview_test);
        listView = (SwipeListView) findViewById(R.id.list);
        adapter = new SlideAdapter(this, listView);
        listView.setAdapter(adapter);
    }

}
