package com.gunnarsturla.restaurantappgi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class SubMenuActivity extends Activity {

	private RecyclerView mRecyclerView;
	private RecyclerView.Adapter mAdapter;
	private RecyclerView.LayoutManager mLayoutManager;
	private int groupNumber;

	private TextView header;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sub_menu);

		groupNumber = 0;

		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			groupNumber = extras.getInt("groupNumber");
		}

		// Setjum nafnið á SubMenuinum inn í headerinn
		header = (TextView) findViewById(R.id.smName);
		header.setText(W8r.get(groupNumber).getName());
		header.setBackgroundResource(R.drawable.sm121);

		//Setjum bakgrunnsmyndina inn í header

		mRecyclerView = (RecyclerView) findViewById(R.id.sub_menu_list);


		// use a linear layout manager
		mLayoutManager = new LinearLayoutManager(this);
		mRecyclerView.setLayoutManager(mLayoutManager);

		// specify an adapter (see also next example)
		mAdapter = new SubMenuAdapter(groupNumber);
		mRecyclerView.setAdapter(mAdapter);
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

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

}
