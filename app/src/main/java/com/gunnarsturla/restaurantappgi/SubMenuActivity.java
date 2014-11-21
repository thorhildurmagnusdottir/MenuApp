package com.gunnarsturla.restaurantappgi;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


public class SubMenuActivity extends Activity {

	private RecyclerView mRecyclerView;
	private RecyclerView.Adapter mAdapter;
	private RecyclerView.LayoutManager mLayoutManager;
	private int groupNumber;

	private TextView header;

	// Það view (ef eitthvað) sem er expanded
	private View expandedCard;

	// Er pantanalistafragmentið sýnilegt?
	private boolean ordrFragVis;
	private OrderFragment orderFragment;

	private View.OnClickListener cardExpander;
	private View.OnClickListener cardCollapser;

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

			FragmentManager fragmentManager = getFragmentManager();
			FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

			if(!ordrFragVis) {
				// Bætum fragmentinu inn í þetta alltsaman
				orderFragment = new OrderFragment();
				fragmentTransaction.add(R.id.smRoot, orderFragment);
				fragmentTransaction.setCustomAnimations(R.anim.enter_from_top, R.anim.exit_to_top);
				fragmentTransaction.addToBackStack(null);

				ordrFragVis = true;

			} else {
				fragmentTransaction.remove(orderFragment);
				fragmentManager.popBackStack();
				ordrFragVis = false;
			}
			fragmentTransaction.commit();

			return true;
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
		ImageButton ordrBtn = (ImageButton) v.findViewById(R.id.orderButton);

		// Náum í parent númer þess cards sem smellt var á
		TextView ptv = (TextView) v.findViewById(R.id.itemParent);
		int parent = Integer.parseInt(ptv.getText().toString());

		// Náum í child númer þess cards sem smellt var á
		TextView ctv = (TextView) v.findViewById(R.id.itemNumber);
		int child = Integer.parseInt(ctv.getText().toString());

		ingredTv.setVisibility(View.VISIBLE);


		calTv.setVisibility(View.VISIBLE);


		ImageView thumb = (ImageView) v.findViewById(R.id.itemThumb);
		//thumb.getLayoutParams().height = ((int) v.getResources().getDimension(R.dimen.card_thumb_height))+100;

		ordrBtn.setVisibility(View.VISIBLE);

		v.setOnClickListener(cardCollapser);
		expandedCard = v;
	}

	public void collapseCard(View v) {
		Log.i("SMActivity", "collapsing card");

		// Fjarlægjum textann sem er í itemCalories og itemIngredients
		TextView ingredTv = (TextView) v.findViewById(R.id.itemIngredients);
		TextView calTv = (TextView) v.findViewById(R.id.itemCalories);
		ImageButton ordrBtn = (ImageButton) v.findViewById(R.id.orderButton);

		ingredTv.setVisibility(View.INVISIBLE);
		calTv.setVisibility(View.INVISIBLE);


		ImageView thumb = (ImageView) v.findViewById(R.id.itemThumb);
		//thumb.getLayoutParams().height = (int) v.getResources().getDimension(R.dimen.card_thumb_height);

		ordrBtn.setVisibility(View.INVISIBLE);

		v.setOnClickListener(cardExpander);
		expandedCard = null;
	}


}