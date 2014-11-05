package com.gunnarsturla.restaurantappgi;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.Vector;

/**
 * Created by Thorleifur on 05/11/14.
 */



public class OrderListAdapter extends BaseAdapter {

   //  ListView ordered;

    //String [] Items; // Hér vantar að kalla á heiti réttar


    @Override
    public int getCount() {
        return Order.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return null;
    }
}
