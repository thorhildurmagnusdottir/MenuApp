package data;

import java.util.Vector;

import menu.Item;
import menu.SubMenu;

/*
 * Created by Gunnar on 26.10.14.
 */
public class W8r {
	private static Vector<SubMenu> w8rMenu;
	//private static SubMenu order;

	public W8r() {
        this.w8rMenu = new Vector<SubMenu>();
        //this.order = new SubMenu();
	}
	public static void build() {
//       ==========================================================
//       Chapter that runs the XML parsing and populates Items.

        XMLHandler theHandler = new XMLHandler();
        XMLParser myParser = new XMLParser(theHandler);
        // Gets the items from the file saved on the pad and parses the xml to items
        myParser.populateItems();
        // returns the populated items
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
//        Log.i("getMenuFromXML", submenuPrinting);
        //  End XMLParsing chapter
        // ==========================================================
		//order = new SubMenu("order", "");
	}

	public static SubMenu get(int number) {
		return w8rMenu.get(number);
	}
	public static int size() {
        if(w8rMenu == null || w8rMenu.isEmpty()) return 0;
        else return w8rMenu.size();
    }
    public static Vector<SubMenu> getW8rMenu() { return w8rMenu; }
//    Counts the number of Items in the menu
	public static int getItemCount()  {
        int items = 0;
        Vector<SubMenu> subMenus = w8rMenu;
        for (SubMenu sm : subMenus){
            int itemsInSubmenu = sm.size();
            items += itemsInSubmenu;
            items ++; // count this submenu as well
        }
        return items;
    }
	// Ný vara pöntuð
//	public static boolean order(Item item) { return order.addItem(item); }
}
