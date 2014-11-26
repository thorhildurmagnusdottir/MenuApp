package com.gunnarsturla.menuapp;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import data.W8r;

/**
 * Created by Gunnar on 23.10.14.
 */
public class MainMenuAdapter extends RecyclerView.Adapter<MainMenuAdapter.ViewHolder> {

	private Context context;
	// Provide a reference to the views for each data item
	// Complex data items may need more than one view per item, and
	// you provide access to all the views for a data item in a view holder
	public static class ViewHolder extends RecyclerView.ViewHolder {

		private TextView smName;
		private View view;
/*		private RecyclerView smRecyclerView;
		private RecyclerView.Adapter smAdapter;
		private RecyclerView.LayoutManager smLayoutManager;*/

		public ViewHolder(View v) {
			super(v);
			smName = (TextView) itemView.findViewById(R.id.smName);
			view = v;
//			smRecyclerView = (RecyclerView) itemView.findViewById(R.id.sub_menu_list);

		}
	}

	// Provide a suitable constructor (depends on the kind of dataset)
	public MainMenuAdapter() {

	}

	// Create new views (invoked by the layout manager)
	@Override
	public MainMenuAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		// create a new view
		View v = LayoutInflater.from(parent.getContext())
				.inflate(R.layout.sub_menu, parent, false);
		// set the view's size, margins, paddings and layout parameters
		System.out.println("Debug: inflating sub_menu ");
		context = parent.getContext();
		return new ViewHolder(v);
	}

	// Replace the contents of a view (invoked by the layout manager)
	@Override
	public void onBindViewHolder(ViewHolder holder, final int position) {


		// - get element from your dataset at this position
		// - replace the contents of the view with that element
		holder.smName.setText(W8r.get(position).getName());
		System.out.println("Debug: setting text for sm" + position);
		Drawable bg = new BitmapDrawable(holder.view.getResources(), W8r.get(position).getBitmap());
		holder.smName.setBackground(bg);

/*		// use a linear layout manager
		holder.smLayoutManager = new LinearLayoutManager(context);
		holder.smRecyclerView.setLayoutManager(holder.smLayoutManager);
		System.out.println("Debug: Setting layout Manager");

		// Set adapter as SubMenuAdapter
		holder.smAdapter = new SubMenuAdapter(position);
		holder.smRecyclerView.setAdapter(holder.smAdapter);
		System.out.println("Debug: setting smAdapter for "+position);
*/
		holder.smName.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//Order.addOrder(W8r.get(groupPosition).get(childPosition));
				((MainActivity)context).goToSubMenu(v, position);
				Log.i("MainMenuAdapter", "Förum yfir í SM " + position);
			}
		});

	}

	// Return the size of your dataset (invoked by the layout manager)
	@Override
	public int getItemCount() {
		return W8r.size();
	}



}