package com.gunnarsturla.menuapp;


import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import menu.Item;
import menu.Order;

/*
 * @autor Gunnar Sturla Ágústuson
 * @since Adapterinn sem setur gögn úr Order.java inn
 * í RecyclerViewið í OrderFragmentinu.
 */

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.ViewHolder> {

    private OrderFragment orderFragment;
	// Provide a reference to the views for each data item
	// Complex data items may need more than one view per item, and
	// you provide access to all the views for a data item in a view holder
	public static class ViewHolder extends RecyclerView.ViewHolder {

		private TextView itemName, itemPrice, itemPosition, itemComment;
		private ImageView thumb;
		private ImageButton deleteBtn;

		public ViewHolder(View v) {
			super(v);
			itemName = (TextView) v.findViewById(R.id.orderItemName);
			itemPrice = (TextView) v.findViewById(R.id.orderItemPrice);
			thumb = (ImageView) v.findViewById(R.id.orderItemThumb);
			deleteBtn = (ImageButton) v.findViewById(R.id.orderItemRemove);
			itemPosition = (TextView) v.findViewById(R.id.orderItemPosition);
			itemComment = (TextView) v.findViewById(R.id.orderItemComment);

		}
	}

	// Provide a suitable constructor (depends on the kind of dataset)
	public OrderListAdapter(OrderFragment orderFrag) {
        orderFragment = orderFrag;
	}

	// Create new views (invoked by the layout manager)
	@Override
	public OrderListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
												   int viewType) {
		// create a new view
		View v = LayoutInflater.from(parent.getContext())
				.inflate(R.layout.single_row_ordered, parent, false);
		// set the view's size, margins, paddings and layout parameters

		return new ViewHolder(v);
	}

	// Replace the contents of a view (invoked by the layout manager)
	@Override
	public void onBindViewHolder(final ViewHolder holder, final int position) {
		// - get element from your dataset at this position
		// - replace the contents of the view with that element

		holder.itemName.setText(Order.get(position).getName());
		holder.itemPrice.setText(Order.get(position).getPrice() + " kr.");
		holder.itemPosition.setText(position + "");
		Log.i("OrderListAdapter:", "Setting position " + position + " to " +Order.get(position).getName());

        if(Order.get(position).getThumbBig() != null) {
			Bitmap iBitmap = Order.get(position).getThumbBig();
			holder.thumb.setImageBitmap(Bitmap.createScaledBitmap(iBitmap, 100, 100, false));
		}

		holder.itemComment.setText(Order.getComment(position));
		Log.i("OLA", "setting comment "+ Order.getComment(position)+ " on "+ position);

		holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View v) {

                Item removed = Order.remove(position);
                notifyDataSetChanged();
                orderFragment.updateTotal();
			    }
			}
		);
	}


    // Return the size of your dataset (invoked by the layout manager)
	@Override
	public int getItemCount() {
		return Order.size();
	}
}
