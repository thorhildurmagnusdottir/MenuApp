package com.gunnarsturla.restaurantappgi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class OrderActivity extends Activity {

    ListView orderList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

		String[] orderArr = new String[Order.size()];

		for(int i = 0; i < Order.size(); i++)
			orderArr[i] = Order.get(i).getName();

        orderList = (ListView) findViewById(R.id.listViewOrdered);
      //  ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, matur);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,R.layout.single_row_ordered,R.id.orderListName,orderArr );
        orderList.setAdapter(adapter);
    }

    private class LayoutInflater {
        public View inflate(int single_row, ViewGroup viewGroup, boolean b) {
            return null;
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {

			Intent intent = new Intent(this, OrderActivity.class);

			startActivity(intent);
			return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
