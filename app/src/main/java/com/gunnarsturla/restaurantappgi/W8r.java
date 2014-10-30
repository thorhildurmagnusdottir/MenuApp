package com.gunnarsturla.restaurantappgi;

import java.util.Vector;

/**
 * Created by Gunnar on 26.10.14.
 */
public class W8r {

	private static Vector<SubMenu> wm;
	private static SubMenu order;

	public W8r() {


	}

	public static void build() {
		//menuParser = new XMLParser();
		//menuParser.parseXML();

		// Hér þarf að setja inn töfraþuluna frá Þórhildi
		// Kalla á XML parserinn, og þann java fæl sem
		// setur gögnin úr XMLinu inn í tréð.
		wm = keyraMenu.build();



		order = new SubMenu();
	}

	public static SubMenu get(int number) {
		return wm.get(number);
	}

	public static int size() { return wm.size();  }

	// Ný vara pöntuð
	public static boolean order(Item item) { return order.addItem(item); }
	// Pöntunin sótt,
	public static SubMenu getOrdered() { return order; }
}
