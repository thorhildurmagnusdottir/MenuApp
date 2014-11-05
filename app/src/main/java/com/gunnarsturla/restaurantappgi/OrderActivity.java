package com.gunnarsturla.restaurantappgi;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class OrderActivity extends Activity {

    ListView Ordered;
    String[] matur = {"kjúklingasalat", "nautafillet", "nautafillet", "Fiskréttur", "súpa dagsins", "Meðlæti", "kaffi", "samloka"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        Ordered = (ListView) findViewById(R.id.listViewOrdered);
      //  ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, matur);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,R.layout.single_row_ordered,R.id.textView,matur );
        Ordered.setAdapter(adapter);
    }

    private class LayoutInflater {
        public View inflate(int single_row, ViewGroup viewGroup, boolean b) {
            return null;
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.order, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
