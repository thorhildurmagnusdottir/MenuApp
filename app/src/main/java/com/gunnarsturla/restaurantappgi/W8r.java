package com.gunnarsturla.restaurantappgi;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import java.io.InputStream;
import java.util.Vector;

/**
 * Created by Gunnar on 26.10.14.
 */
public class W8r {

	private static Vector<SubMenu> w8rMenu;
	private static SubMenu order;
	public W8r() {
        this.w8rMenu = new Vector<SubMenu>();
        this.order = new SubMenu();
	}
	public static void build(Context theContext) {
        // ==========================================================
        // Chapter that runs the XML parsing and populates Items.
        InputStream menuXML;   // returns InputStream
        Resources menuRes = theContext.getResources();
        menuXML = menuRes.openRawResource(R.raw.w8rmenu); //returns InputStream
        XMLHandler theHandler = new XMLHandler();
        XMLParser myParser = new XMLParser(menuXML, theHandler);
        myParser.parseXML();
        Vector<SubMenu> newMenu = myParser.totalMenu;
        w8rMenu = myParser.totalMenu;
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
	}

	public static SubMenu get(int number) {
		return w8rMenu.get(number);
	}

	public static int size() { return w8rMenu.size();  }

	// Ný vara pöntuð
	public static boolean order(Item item) { return order.addItem(item); }
	// Pöntunin sótt,
	public static SubMenu getOrdered() { return order; }
}
