package com.gunnarsturla.menuapp;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

/**
 * @author Gunnar Sturla Ágústuson
 * @since 8.10.14
 * Aðalklasinn sem birtir útlit forritsins og ræsir það.
 */
public class MainActivity extends Activity {


	private static RecyclerView mRecyclerView;
	private static RecyclerView.Adapter mAdapter;
	private static RecyclerView.LayoutManager mLayoutManager;

    private boolean ordrFragVis;
    private OrderFragment orderFragment;

	// Skilgreini context hér til að geta náð í það hvar sem er með kallinu MainActivity.context
	// (Jaaaá, Snorri mundi örugglega skamma mig fyrir að brjóta
	// upplýsingahuld, en hann mun ekki sjá þetta (vona ég))
	public static Context context;

	public MainActivity() {
	}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

		context = getApplicationContext();

        Log.i("MainActivity", "ran here");
//        Context theContext = getApplicationContext();
        displayMenu();
	}

    public void displayMenu(){
        //		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new MainMenuAdapter();
        mRecyclerView.setAdapter(mAdapter);
/*
		TextView smName = (TextView) findViewById(R.id.smName);
		smName.setText(W8r.get(0).getName());*/

		//Test, má henda út
//		Order.addOrder(W8r.get(1).get(1));
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.i("onCreatOptionsMenu","running");
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
        if (id == R.id.action_viewOrder) {

            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            if(!ordrFragVis) {
                // Bætum fragmentinu inn í þetta alltsaman
                fragmentTransaction.setCustomAnimations(R.animator.enter_from_top, R.animator.exit_to_top);

                orderFragment = new OrderFragment();
                fragmentTransaction.add(R.id.smRoot, orderFragment);
                fragmentTransaction.addToBackStack(null);

                ordrFragVis = true;

            } else {
                fragmentTransaction.setCustomAnimations(R.animator.exit_to_top,R.animator.exit_to_top);

                fragmentTransaction.remove(orderFragment);
                fragmentManager.popBackStack();
                ordrFragVis = false;
            }
            fragmentTransaction.commit();

            return true;
        }
        else if (id == R.id.action_callWaiter) {

            CallWaiter.callme(this);
        }

        return super.onOptionsItemSelected(item);
    }
	/*  goToAdd ræsir, og færir stjórnina yfir í DoAddActivity
 *  view er það view sem sendir beiðnina
 */
	public void goToSubMenu(View view, int groupNumber) {
		Intent intent = new Intent(this, SubMenuActivity.class);
		intent.putExtra("groupNumber", groupNumber);
		startActivity(intent);
	}

}
