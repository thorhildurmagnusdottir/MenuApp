package com.gunnarsturla.restaurantappgi;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.Vector;

import menu.SubMenu;

/**
 * @author Gunnar Sturla Ágústuson
 * @since 14.10.14
 * Klasinn sem lætur matseðilinn birtast í expandable list
 */

public class MenuAdapter extends BaseExpandableListAdapter {

	private Context context;
//	private SubMenu[] subMenuList;
	private Vector<SubMenu> wm;
	private SubMenu order;

	public MenuAdapter(Context context, Vector<SubMenu> wm, SubMenu order) {
		this.context = context;
		this.wm = wm;
		this.order = order;
		//subMenuList = wm.getSubMenus();
	}

	@Override
	public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
		// Tímabundið textview, til að tékka hvort allt virki ekki.
		// Í framtíðinni á að koma bakgrunnsmynd og fleira fallegt.
		TextView tv = new TextView(context);
		// Þetta finnst mér geðveikt kúl:
		tv.setText(	wm.get(groupPosition).get(childPosition).getName() +
					"\n" +
					wm.get(groupPosition).get(childPosition).getPrice() +
					" kr.\n\n" +
					wm.get(groupPosition).get(childPosition).getDescription());
		tv.setTextSize(20);
		tv.setPadding(10,5,10,5);

		tv.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				order.addItem(wm.get(groupPosition).get(childPosition));
				System.out.println("Pöntun tókst!" + wm.get(groupPosition).get(childPosition).getName());
			}
		});

		return tv;



	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return wm.get(groupPosition).size();
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return null;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return wm.get(groupPosition).get(childPosition).getId();
	}

	@Override
	public int getGroupCount() {
		return wm.size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return null;
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
		TextView tv = new TextView(context);
		tv.setText(wm.get(groupPosition).getName());
		tv.setTextSize(35);
		tv.setPadding(60, 100, 30, 5);


/*		Resources res = context.getResources();

		Drawable drawable = res.getDrawable(R.drawable.sm121);
		tv.setBackground(drawable);*/
		tv.setHeight(220);

		return tv;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}


}
