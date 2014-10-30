package com.gunnarsturla.restaurantappgi;

import java.util.Vector;

/**
 * @author Gunnar Sturla Ágústuson
 * @since 9.10.14
 * Klasinn sem heldur utan um yfirmatseðil í matseðlatréinu og setur hvern
 * undirmatseðil í yfirmatseðilinn.
 */
public class W8rMenu {
	private Vector<SubMenu> subMenus;


	public W8rMenu() {

		subMenus = new Vector<SubMenu>();
	}

/*	public SubMenu[] getSubMenus() {
		SubMenu sm[] = new SubMenu[subMenus.size()];
		subMenus.toArray(sm);
		return sm;
	}*/

    public boolean addSubMenu(SubMenu sm) { return subMenus.add(sm); }

	public SubMenu getSubMenu(int i) {
		return subMenus.get(i);
	}
	public int length() { return subMenus.size(); }

}