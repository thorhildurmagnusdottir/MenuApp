package data;

import java.util.Vector;

import menu.SubMenu;

/*
 * @author Gunnar
 * @since 26.10.14.
 * This is the shit. Grunnurinn sem heldur utan um allan matseðilinn
 * Item (hver réttur) eru geymd í SubMenu-um sem W8r heldur utan um.
 */
public class W8r {
	private static Vector<SubMenu> w8rMenu;

	public W8r() {
        this.w8rMenu = new Vector<SubMenu>();
	}
	public static void build() {
//       ==========================================================
//       Chapter that runs the XML parsing and populates Items.

        XMLHandler theHandler = new XMLHandler();
        XMLParser myParser = new XMLParser(theHandler);
        // Gets the items from the file saved on the pad and parses the xml to items
        myParser.populateItems();
        // returns the populated items
        w8rMenu = myParser.populateMenu();
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
}
