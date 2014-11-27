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
import android.widget.EditText;
import android.widget.TextView;

import menu.Order;

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
	private View editingItem;

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

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new MainMenuAdapter();
        mRecyclerView.setAdapter(mAdapter);
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

			// Birtum orderFragmentið með kalli á fall
			orderFragment = MainActivity.openOrderFragment(getFragmentManager(), ordrFragVis, R.id.mmRoot);
			ordrFragVis = !ordrFragVis;

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

	public static OrderFragment openOrderFragment(FragmentManager fragmentManager, boolean ordrFragVis, int viewGroupId) {

		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

		OrderFragment orderFrag = new OrderFragment();

		if(!ordrFragVis) {
			// Bætum fragmentinu inn í þetta alltsaman
			fragmentTransaction.setCustomAnimations(R.animator.enter_from_top, R.animator.exit_to_top);

			fragmentTransaction.add(viewGroupId, orderFrag);
			fragmentTransaction.addToBackStack(null);

		} else {
			fragmentTransaction.setCustomAnimations(R.animator.exit_to_top,R.animator.exit_to_top);

			fragmentTransaction.remove(orderFrag);
			fragmentManager.popBackStack();
		}
		fragmentTransaction.commit();
		return orderFrag;
	}

	public void enableComment(View v) {

		Log.i("OrderFragment:", "clicking to enable comment");
		if(editingItem != null)
			disableComment(editingItem);

		TextView commentView = (TextView) v.findViewById(R.id.orderItemComment);
		EditText editComment = (EditText) v.findViewById(R.id.orderEditComment);
		TextView itemPosition = (TextView) v.findViewById(R.id.orderItemPosition);

		int pos = Integer.parseInt((String) itemPosition.getText());


		String comment = Order.getComment(pos);

		editComment.setText(comment);

		commentView.setVisibility(View.INVISIBLE);
		editComment.setVisibility(View.VISIBLE);

		v.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				disableComment(v);
			}
		});

		editingItem = v;
	}

	public void disableComment(View v) {

		v = editingItem;

		TextView commentView = (TextView) v.findViewById(R.id.orderItemComment);
		EditText editComment = (EditText) v.findViewById(R.id.orderEditComment);
		TextView itemPosition = (TextView) v.findViewById(R.id.orderItemPosition);

		int pos = Integer.parseInt((String) itemPosition.getText());
		String comment = editComment.getText().toString();

		Order.setComment(pos, comment);

		commentView.setText(comment);
		Log.i("SMA", "setting comment "+ comment+ " on "+ pos);

		commentView.setVisibility(View.VISIBLE);
		editComment.setVisibility(View.INVISIBLE);

		v.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				enableComment(v);
			}
		});

		editingItem = null;
	}

}
