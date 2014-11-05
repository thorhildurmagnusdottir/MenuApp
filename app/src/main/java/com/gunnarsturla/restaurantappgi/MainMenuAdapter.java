package com.gunnarsturla.restaurantappgi;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
		private RecyclerView smRecyclerView;
		private RecyclerView.Adapter smAdapter;
		private RecyclerView.LayoutManager smLayoutManager;

		public ViewHolder(View v) {
			super(v);
			smName = (TextView) itemView.findViewById(R.id.smName);
			smRecyclerView = (RecyclerView) itemView.findViewById(R.id.sub_menu_list);


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
	public void onBindViewHolder(ViewHolder holder, int position) {
		// - get element from your dataset at this position
		// - replace the contents of the view with that element
		holder.smName.setText(W8r.get(position).getName());
		System.out.println("Debug: setting text for sm" + position);

		// use a linear layout manager
		holder.smLayoutManager = new LinearLayoutManager(context);
		holder.smRecyclerView.setLayoutManager(holder.smLayoutManager);
		System.out.println("Debug: Setting layout Manager");

		// Set adapter as SubMenuAdapter
		holder.smAdapter = new SubMenuAdapter(position);
		holder.smRecyclerView.setAdapter(holder.smAdapter);
		System.out.println("Debug: setting smAdapter for "+position);


	}

	// Return the size of your dataset (invoked by the layout manager)
	@Override
	public int getItemCount() {
		return W8r.size();
	}
}