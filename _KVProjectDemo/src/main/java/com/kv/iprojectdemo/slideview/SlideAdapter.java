package com.kv.iprojectdemo.slideview;

import java.util.ArrayList;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.kv.iprojectdemo.R;
import com.kv.iprojectlib.plugin.slidelistview.SwipeListView;

public class SlideAdapter extends BaseAdapter {

    private Activity act;
    private ArrayList<String> list = new ArrayList<String>();
    private SwipeListView listView;
    
    public SlideAdapter(Activity act, SwipeListView listView) {
        this.act = act;
        this.listView = listView;
        for (int i = 0; i < 10; i++) {
            list.add("item" + i);
        }
    }
    
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = act.getLayoutInflater().inflate(R.layout.slideview_test_list_item, null);
            holder = new ViewHolder();
            holder.nameTV = (TextView) convertView.findViewById(R.id.tv1);
            holder.editBtn = (Button) convertView.findViewById(R.id.edit_btn);
            holder.deteleBtn = (Button) convertView.findViewById(R.id.delete_btn);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.nameTV.setText(list.get(position));
        holder.editBtn.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                Toast.makeText(act, "edit" + position, Toast.LENGTH_SHORT).show();
                listView.hideShowingItem();
            }
        });
        holder.deteleBtn.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                Toast.makeText(act, "delete" + position, Toast.LENGTH_SHORT).show();
                listView.hideShowingItem();
            }
        });
        return convertView;
    }

    class ViewHolder {
        TextView nameTV;
        Button editBtn;
        Button deteleBtn;
    }
}
