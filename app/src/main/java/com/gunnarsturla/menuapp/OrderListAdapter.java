package com.gunnarsturla.menuapp;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import menu.Order;

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
        return Order.get(i);
    }

    @Override
    public long getItemId(int i) {
        return Order.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {


		return null;
    }
}
