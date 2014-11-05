package com.gunnarsturla.restaurantappgi;

import android.util.Log;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.List;
import java.util.Vector;

/**
 * @author Þórhildur Magnúsdóttir
 * @since 12.10.14
 * Þessi klasi bregst við gögnum úr XML skránni og býr til hluti
 */
public class XMLHandler extends DefaultHandler {

    // List to hold all menu items fro xml file
    private SubMenu subMenu;
    private List<Item> items;
    private Item currentItem;
    private Vector<SubMenu> allSubMenus;
    public XMLHandler() {
        Log.i("XMLHandler", "Created one");
        currentItem = new Item();
        items = new Vector<Item>();
        subMenu = new SubMenu("Submenu1", "");
        allSubMenus = new Vector<SubMenu>();
    }
    public List<Item> getItems() {
        return items;
    }
    public SubMenu getSubMenu() {
        return subMenu;
    }
    public Vector<SubMenu> getMenu() { return allSubMenus; }
    // boolean variables for all Item variables
    boolean bName = false;
    boolean bPrice = false;
    boolean bDesc = false;
    boolean bIngr = false;
    boolean bCals = false;
    boolean bImgh = false;
    boolean sName = false;
    boolean sImgh = false;

    /*  Set each boolean variable as true when an element is started to tell the handler that he
     *  can set the appropriate Item attribute (in method characters()).
     */
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException{
        if (qName.equalsIgnoreCase("submenu")){
            subMenu = new SubMenu("submenuTest", "");
//            Log.i("startSubmenu", "lets create a submenu");
        }
        else if (qName.equalsIgnoreCase("title")){
            sName = true;
//            Log.i("Submenu", "has name");
        }
        else if (qName.equalsIgnoreCase("simgh")){
            sImgh = true;
        }
        else if (qName.equalsIgnoreCase("item")){
//            Log.i("START", String.format("Start element: %s", qName));
            int id = Integer.parseInt(attributes.getValue("id"));
            currentItem = new Item(id);
            if (null == items){
//                Log.i("StartElement", "items is null");
                items = new Vector<Item>();
            }
            // TODO create special method for submenus
        }
        else if (qName.equalsIgnoreCase("name")){
            bName = true;
        }
        else if (qName.equalsIgnoreCase("price")){
            bPrice = true;
        }
        else if (qName.equalsIgnoreCase("description")){
            bDesc = true;
        }
        else if (qName.equalsIgnoreCase("ingredients")){
            bIngr = true;
        }
        else if (qName.equalsIgnoreCase("calories")){
            bCals = true;
        }
        else if (qName.equalsIgnoreCase("name")){
            bImgh = true;
        }
    }
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException{
        if (qName.equalsIgnoreCase("item")){
            items.add(currentItem);
            subMenu.addItem(currentItem);
//            Log.i("endItem", "hopefully added item to submenu");
        }
        else if (qName.equalsIgnoreCase("submenu")){
//            Log.i("endSubmenu", "lets create a submenu here" + items.size());
            allSubMenus.add(subMenu);
        }
        // TODO add items to different submenus accordingly
    }
    @Override
    public void characters(char ch[], int start, int length) throws SAXException{

        if (sName) {
//            Log.i("characters", "this Submenu has a name");
            String name = new String(ch, start, length);
//            String settingName = "setSubmenuName";
            subMenu.setName(name);
            sName = false;
        }
        else if (sImgh) {
//            Log.i("characters", "this Submenu has a name");
            String hash = new String(ch, start, length);
            subMenu.setImghash(hash);
            sName = false;
        }
        else if (bName) {
//            Log.i("characters", "this item has a name");
            String name = new String(ch, start, length);
            currentItem.setName(name);
            bName = false;
        }
        else if (bDesc) {
            String description = new String(ch, start, length);
//            Log.i("characters", "description: " + description);
            currentItem.setDescription(description);
            bDesc = false;
        }
        else if (bIngr) {
            String ingredients = new String(ch, start, length);
//            Log.i("characters", "description: " + ingredients);
            currentItem.setIngredients(ingredients);
            bIngr = false;
        }
        else if (bImgh) {
            String imghash = new String(ch, start, length);
//            Log.i("characters", "description: " + imghash);
            currentItem.setImghash(imghash);
            bImgh = false;
        }
        else if (bCals) {
//            Log.i("Characters", "bPrice is true");
            int cals = Integer.parseInt(new String(ch, start, length));
            currentItem.setCalories(cals);
            bCals = false;
        }
        else if (bPrice) {
//            Log.i("Characters", "bPrice is true");
            int price = Integer.parseInt(new String(ch, start, length));
            currentItem.setPrice(price);
            bPrice = false;
        }
        // TODO create else if for all other variables
    }
}
