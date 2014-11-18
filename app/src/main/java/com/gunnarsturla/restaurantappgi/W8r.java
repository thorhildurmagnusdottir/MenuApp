package com.gunnarsturla.restaurantappgi;

import android.util.Log;

import java.util.Vector;

import menu.Item;
import menu.SubMenu;

/*
 * Created by Gunnar on 26.10.14.
 */
public class W8r {
    public static MainActivity mainActivity;
	private static Vector<SubMenu> w8rMenu;
	private static SubMenu order;
	public W8r() {
        this.w8rMenu = new Vector<SubMenu>();
        this.order = new SubMenu();
	}
	public static void build() {
//       ==========================================================
//       Chapter that runs the XML parsing and populates Items.

        XMLHandler theHandler = new XMLHandler();
        XMLParser myParser = new XMLParser(theHandler);
        // Gets the items from the file saved on the pad and parses the xml to items
        myParser.populateItems();
        // returns the populated itesm
        Vector<SubMenu> newMenu = myParser.populateMenu();
        w8rMenu = newMenu;
        // ==========================================================
        // TEST PRINTING
        String submenuPrinting = "";
        if (null != newMenu){
            for (SubMenu sm : newMenu) {
                String submenuName = sm.getName();
                submenuPrinting = submenuPrinting + submenuName + "\n";
                for (Item i : sm.getItems()){
                    String itemName = i.getName();
                    submenuPrinting = submenuPrinting + itemName + "\n";
                }
            }
        }
        submenuPrinting = submenuPrinting + "submenu er null";
        Log.i("getMenuFromXML", submenuPrinting);
        //  End XMLParsing chapter
        // ==========================================================
		order = new SubMenu();
//        mainActivity.displayMenu();
	}

	public static SubMenu get(int number) {
		return w8rMenu.get(number);
	}

	public static int size() { return w8rMenu.size();  }

	// Ný vara pöntuð
	public static boolean order(Item item) { return order.addItem(item); }
}
