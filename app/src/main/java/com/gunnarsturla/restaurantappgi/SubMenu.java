package com.gunnarsturla.restaurantappgi;


import android.util.Log;

import java.util.Vector;

/**
 * @author Gunnar Sturla Ágústuson
 * @since 14.10.14
 * Klasinn sem heldur utan um undirmatseðil í matseðlatréinu (t.d. Forréttir, drykkir, etc.)
 * og setur hvern rétt í ákveðinn undirmatseðil.
 */

public class SubMenu {
    private String 	name,
            imghash;
    private Vector<Item> items;

    public SubMenu(String name, String imghash) {
        this.name 		 = name;
        this.imghash	 = imghash;
        items = new Vector<Item>();
        Log.i("submenu", "created one");
    }

    // Þetta er eintak af SubMenu sem heldur
    // utan um pöntunina sem á eftir að senda.
    public SubMenu() {
        this.name = "order";
        this.imghash = "";
        items = new Vector<Item>();
    }
    public Vector<Item> getItems() {
        return items;
    }
    public Item removeItem(int i) { return items.remove(i); }

    public boolean isEmpty() { return items.isEmpty(); }
    public boolean addItem(Item i) {
        return items.add(i);
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setImghash(String hash) { this.imghash = hash; }
    public int 	  size()			{ return items.size();	}
    public String getName()   		{ return name; 			}
    public String getImghash()		{ return imghash;   	}
    public Item get(int i)	{ return items.get(i);	}

    // eyðir öllu út úr items, en bara ef þetta er pöntun
    public boolean reset() {
        if(this.name.equals("order")) {
            items = new Vector<Item>();
            return true;
        } else {
            return false;
        }
    }

}