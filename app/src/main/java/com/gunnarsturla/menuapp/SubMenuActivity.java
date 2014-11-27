package com.gunnarsturla.menuapp;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import data.W8r;
import menu.Order;


public class SubMenuActivity extends Activity {

	private RecyclerView mRecyclerView;
	private RecyclerView.Adapter mAdapter;
	private RecyclerView.LayoutManager mLayoutManager;
	private int groupNumber;

	private TextView header;

	// Það view (ef eitthvað) sem er expanded
	private View expandedCard;

	private View editingItem;

	// Er pantanalistafragmentið sýnilegt?
	private boolean ordrFragVis;
	private OrderFragment orderFragment;

	private View.OnClickListener cardExpander;
	private View.OnClickListener cardCollapser;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sub_menu);

		ordrFragVis = false;
		groupNumber = 0;

		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			groupNumber = extras.getInt("groupNumber");
		}

		// Setjum nafnið á SubMenuinum inn í headerinn
		header = (TextView) findViewById(R.id.smName);
		header.setText(W8r.get(groupNumber).getName());
		Drawable bg = new BitmapDrawable(getResources(), W8r.get(groupNumber).getBitmap());
		header.setBackground(bg);
		//header.setBackgroundResource(R.drawable.sm121);

		//Setjum bakgrunnsmyndina inn í header

		mRecyclerView = (RecyclerView) findViewById(R.id.sub_menu_list);


		// use a linear layout manager
		mLayoutManager = new LinearLayoutManager(this);
		mRecyclerView.setLayoutManager(mLayoutManager);

		// specify an adapter (see also next example)
		mAdapter = new SubMenuAdapter(groupNumber);
		mRecyclerView.setAdapter(mAdapter);

		// Hlustar eftir smellum þegar card er expanded, og minnkar það.
		cardCollapser = new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				collapseCard(v);
			}
		};

		cardExpander = new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				expandCard(v);
				if(ordrFragVis) {
					orderFragment = MainActivity.openOrderFragment(getFragmentManager(), ordrFragVis, R.id.smRoot);
					ordrFragVis = !ordrFragVis;
				}
			}
		};
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
		if (id == R.id.action_viewOrder) {

			// Opnum / lokum OrderFragment
			orderFragment = MainActivity.openOrderFragment(getFragmentManager(), ordrFragVis, R.id.smRoot);
			ordrFragVis = !ordrFragVis;

			// Og lokum cardinu í leiðinni (ef það var opið)
			if(expandedCard != null) {
				collapseCard(expandedCard);
			}

			return true;
		}
        else if (id == R.id.action_callWaiter) {
            CallWaiter.callme(this);
        }

		return super.onOptionsItemSelected(item);
	}

	public void expandCard(View v) {
		Log.i("SMActivity", "expanding card");

		// Byrjum á því að fella niður það card sem var opið (ef eitthvað)
		if(expandedCard != null) {
			collapseCard(expandedCard);
		}

		TextView ingredTv = (TextView) v.findViewById(R.id.itemIngredients);
		TextView calTv = (TextView) v.findViewById(R.id.itemCalories);
		TextView itemDesc = (TextView) v.findViewById(R.id.itemDestription);
		ImageButton ordrBtn = (ImageButton) v.findViewById(R.id.orderButton);

		// Sýnum elementin sem birtast þegar smellt er
		ingredTv.setVisibility(View.VISIBLE);
		calTv.setVisibility(View.VISIBLE);
		ordrBtn.setVisibility(View.VISIBLE);

		// Færum lýsingartextann til hægri til
		// að búa til pláss fyrir panta takkann
		RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)itemDesc.getLayoutParams();
		params.setMargins(45, 16, 8, 8); //substitute parameters for left, top, right, bottom
		itemDesc.setLayoutParams(params);


		//ImageView thumb = (ImageView) v.findViewById(R.id.itemThumb);
		//thumb.getLayoutParams().height = ((int) v.getResources().getDimension(R.dimen.card_thumb_height))+100;

		if(ordrFragVis) {
			orderFragment = MainActivity.openOrderFragment(getFragmentManager(), ordrFragVis, R.id.smRoot);
			ordrFragVis = !ordrFragVis;
		}

		// Setjum fellismellihlustarann á cardið
		v.setOnClickListener(cardCollapser);
		expandedCard = v;
	}

	public void collapseCard(View v) {
		Log.i("SMActivity", "collapsing card");

		// Fjarlægjum textann sem er í itemCalories og itemIngredients
		TextView ingredTv = (TextView) v.findViewById(R.id.itemIngredients);
		TextView calTv = (TextView) v.findViewById(R.id.itemCalories);
		TextView itemDesc = (TextView) v.findViewById(R.id.itemDestription);
		ImageButton ordrBtn = (ImageButton) v.findViewById(R.id.orderButton);

		// Felum það sem þarf að fela
		ingredTv.setVisibility(View.INVISIBLE);
		calTv.setVisibility(View.INVISIBLE);
		ordrBtn.setVisibility(View.INVISIBLE);

		// Færum lýsingartextann til baka á sama stað
		RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)itemDesc.getLayoutParams();
		//substitute parameters for left, top, right, bottom
		params.setMargins(0, 16, 8, 8);
		itemDesc.setLayoutParams(params);

		//ImageView thumb = (ImageView) v.findViewById(R.id.itemThumb);
		//thumb.getLayoutParams().height = (int) v.getResources().getDimension(R.dimen.card_thumb_height);


		v.setOnClickListener(cardExpander);
		expandedCard = null;
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