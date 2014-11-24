package com.gunnarsturla.menuapp;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import data.W8r;
import menu.Item;
import menu.Order;

/**
 * Created by Gunnar
 * Date: 23.10.14
 * Description:
 */
public class SubMenuAdapter extends RecyclerView.Adapter<SubMenuAdapter.ViewHolder> {
	//private W8r w8r;
	private int parentNumber;
	// Provide a reference to the views for each data item
	// Complex data items may need more than one view per item, and
	// you provide access to all the views for a data item in a view holder
	public static class ViewHolder extends RecyclerView.ViewHolder {
		// each data item is just a string in this case
		public TextView itemName, itemDescription, itemPrice, itemCalories, itemNumber, itemParent, itemIngredients;
		private ImageView itemThumb;
		private ImageButton orderButton;
//		private CardView itemCard;


		private CardView itemCard;

		public ViewHolder(View v) {
			super(v);
			itemName		= (TextView) itemView.findViewById(R.id.itemName);
			itemDescription	= (TextView) itemView.findViewById(R.id.itemDestription);
			itemPrice		= (TextView) itemView.findViewById(R.id.itemPrice);
			itemCalories 	= (TextView) itemView.findViewById(R.id.itemCalories);
			itemNumber 		= (TextView) itemView.findViewById(R.id.itemNumber);
			itemParent		= (TextView) itemView.findViewById(R.id.itemParent);
			itemIngredients = (TextView) itemView.findViewById(R.id.itemIngredients);

			orderButton		= (ImageButton) itemView.findViewById(R.id.orderButton);
			itemThumb		= (ImageView) itemView.findViewById(R.id.itemThumb);
//			itemCard 		= (CardView) itemView.findViewById(R.id.itemCard);
		}
	}

	// Provide a suitable constructor (depends on the kind of dataset)
	public SubMenuAdapter(int parentNumber) {
		this.parentNumber = parentNumber;
		System.out.println("Debug: parentNumber" + parentNumber);
	}

	// Create new views (invoked by the layout manager)
	@Override
	public SubMenuAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
														 int viewType) {
		// create a new view
		View v = LayoutInflater.from(parent.getContext())
				.inflate(R.layout.card_item, parent, false);
		// set the view's size, margins, paddings and layout parameters
		System.out.println("Debug: inflating card number "+ parentNumber);
		return new ViewHolder(v);
	}

	// Replace the contents of a view (invoked by the layout manager)
	@Override
	public void onBindViewHolder(final ViewHolder holder, final int position) {
		// - get element from your dataset at this position
		// - replace the contents of the view with that element
		holder.itemName.setText(W8r.get(parentNumber).get(position).getName());
		holder.itemDescription.setText(W8r.get(parentNumber).get(position).getDescription());
		holder.itemPrice.setText(""+ W8r.get(parentNumber).get(position).getPrice() + " kr.");



		// Smellum parentNumber og itemNumber inn í falin TextView;
		holder.itemNumber.setText(""+position);
		holder.itemParent.setText(""+parentNumber);
        holder.itemThumb.setImageBitmap(W8r.get(parentNumber).get(position).getThumbBig());

		// Sæki Recource ID á thumb1123.jpg til að birta sem bg
//		int thumbId = MainActivity.context.getResources().getIdentifier("thumb1123.png", "drawable", MainActivity.context.getPackageName());
//		holder.itemThumb.setImageResource(thumbId);


		//int drawableResourceId = this.getResources().getIdentifier("nameOfDrawable", "drawable", this.getPackageName());

		holder.orderButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				Item i = W8r.get(parentNumber).get(position);

				if(Order.addOrder(i)) {
					Log.i("SubMenuAdapter", "Pantaði " + i.getName());
					Toast.makeText(MainActivity.context, "Bætti " + i.getName() + " við pöntun.", Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(MainActivity.context, "Tókst ekki að bæta " + i.getName() + " við pöntun.", Toast.LENGTH_SHORT).show();
				}
			}
		}
		);


//		holder.itemPrice.setText(W8r.get(parentNumber).get(position).getPrice());
		System.out.println("Debug: getting elements from parentNumber: "+ parentNumber);


	}
	// Return the size of your dataset (invoked by the layout manager)
	@Override
	public int getItemCount() {
		return W8r.get(parentNumber).size();
	}
}