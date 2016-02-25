package com.kv.iprojectlib.plugin.pulltorefresh;

import com.kv.iprojectlib.plugin.pulltorefresh.XListView.IXListViewListener;
import com.kv.iprojectlib.utils.DateUtils;

public class XListViewHelper {

    public static void initXListView(XListView xListView, boolean isPullRefresh, boolean isPullLoad, boolean isAutoLoad, IXListViewListener listener) {
        xListView.setPullRefreshEnable(false);
        xListView.setPullLoadEnable(true);
        xListView.setAutoLoadEnable(true);
        xListView.setXListViewListener(listener);
        xListView.setRefreshTime(DateUtils.getChinaTime());
    }
    
    public static void onPostInitXListView(XListView xListView) {
        xListView.stopRefresh();
        xListView.stopLoadMore();
        xListView.setRefreshTime(DateUtils.getChinaTime());
        xListView.setPullLoadEnable(true);
    }
    
    public static void onPostErrorXlistView(XListView xListView) {
        onPostInitXListView(xListView);
        xListView.setFootLoadError();
    }
    
    public static void onDefinedLoadMore(XListView xListView, int adapterCount, int dataCount) {
        if (adapterCount >= dataCount) {
            xListView.setPullLoadEnable(false);
        }
    }
}
