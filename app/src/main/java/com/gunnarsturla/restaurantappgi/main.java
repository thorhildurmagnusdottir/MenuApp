package com.gunnarsturla.restaurantappgi;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

/**
 * @author Gunnar Sturla Ágústuson
 * @since 8.10.14
 * Aðalklasinn sem birtir útlit forritsins og ræsir það.
 */
public class main extends Activity {

//	private W8r w8r;

	private RecyclerView mRecyclerView;
	private RecyclerView.Adapter mAdapter;
	private RecyclerView.LayoutManager mLayoutManager;

	public main() {
	}


	//private XMLParser menuParser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("main", "ran here");

		W8r.build();

		mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);


		// use a linear layout manager
		mLayoutManager = new LinearLayoutManager(this);
		mRecyclerView.setLayoutManager(mLayoutManager);

		// specify an adapter (see also next example)
		mAdapter = new SubMenuAdapter(0);
		mRecyclerView.setAdapter(mAdapter);


	}


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the Menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar Item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

	/*  goToAdd ræsir, og færir stjórnina yfir í DoAddActivity
 *  view er það view sem sendir beiðnina

	public void goToSubMenu(MenuItem item) {

		Intent intent = new Intent(this, SubMenuActivity.class);

		startActivity(intent);
	}*/
}
