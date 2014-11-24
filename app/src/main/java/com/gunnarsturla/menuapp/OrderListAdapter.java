package com.gunnarsturla.menuapp;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import menu.Order;

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.ViewHolder> {

	// Provide a reference to the views for each data item
	// Complex data items may need more than one view per item, and
	// you provide access to all the views for a data item in a view holder
	public static class ViewHolder extends RecyclerView.ViewHolder {

		private TextView itemName, itemPrice;
		private ImageView thumb;
		private Button deleteBtn;
		private View v;

		public ViewHolder(View v) {
			super(v);
			this.v = v;
			itemName = (TextView) v.findViewById(R.id.orderItemName);
			itemPrice = (TextView) v.findViewById(R.id.orderItemPrice);
			thumb = (ImageView) v.findViewById(R.id.orderItemThumb);
		//	deleteBtn = (Button) v.findViewById(R.id.orderItemRemove);
		}
	}

	// Provide a suitable constructor (depends on the kind of dataset)
	public OrderListAdapter() {

	}

	// Create new views (invoked by the layout manager)
	@Override
	public OrderListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
												   int viewType) {
		// create a new view
		View v = LayoutInflater.from(parent.getContext())
				.inflate(R.layout.single_row_ordered, parent, false);
		// set the view's size, margins, paddings and layout parameters

		ViewHolder vh = new ViewHolder(v);
		return vh;
	}

	// Replace the contents of a view (invoked by the layout manager)
	@Override
	public void onBindViewHolder(ViewHolder holder, final int position) {
		// - get element from your dataset at this position
		// - replace the contents of the view with that element

		holder.itemName.setText(Order.get(position).getName());
		holder.itemPrice.setText(Order.get(position).getPrice() + " kr.");
		Drawable img = holder.v.getContext().getResources().getDrawable(R.drawable.ic_launcher);
		holder.thumb.setImageDrawable(img);
/*		holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
												@Override
												public void onClick(View v) {

					Item removed = Order.remove(position);
					// TODO: Senda removed í fragmentið

				}
			}
		);*/

	}

	// Return the size of your dataset (invoked by the layout manager)
	@Override
	public int getItemCount() {
		return Order.size();
	}
}
