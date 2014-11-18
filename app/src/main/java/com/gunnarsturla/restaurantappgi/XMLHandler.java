package com.gunnarsturla.restaurantappgi;

import android.util.Log;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.List;
import java.util.Vector;

import menu.Item;
import menu.SubMenu;

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
        }
        else if (qName.equalsIgnoreCase("title")){
            sName = true;
        }
        else if (qName.equalsIgnoreCase("simgh")){
            sImgh = true;
        }
        else if (qName.equalsIgnoreCase("item")){
            int id = Integer.parseInt(attributes.getValue("id"));
            Log.i("start element item", Integer.toString(id));
            currentItem = new Item(id);
            if (null == items){
                items = new Vector<Item>();
            }
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
        }
        else if (qName.equalsIgnoreCase("submenu")){
            allSubMenus.add(subMenu);
        }
    }
    @Override
    public void characters(char ch[], int start, int length) throws SAXException{

        if (sName) {
            String name = new String(ch, start, length);
            subMenu.setName(name);
            sName = false;
        }
        else if (sImgh) {
            String hash = new String(ch, start, length);
            subMenu.setImghash(hash);
            sName = false;
        }
        else if (bName) {
            String name = new String(ch, start, length);
            currentItem.setName(name);
            bName = false;
        }
        else if (bDesc) {
            String description = new String(ch, start, length);
            currentItem.setDescription(description);
            bDesc = false;
        }
        else if (bIngr) {
            String ingredients = new String(ch, start, length);
            currentItem.setIngredients(ingredients);
            bIngr = false;
        }
        else if (bImgh) {
            String imghash = new String(ch, start, length);
            currentItem.setImghash(imghash);
            bImgh = false;
        }
        else if (bCals) {
            int cals = Integer.parseInt(new String(ch, start, length));
            currentItem.setCalories(cals);
            bCals = false;
        }
        else if (bPrice) {
            int price = Integer.parseInt(new String(ch, start, length));
            currentItem.setPrice(price);
            bPrice = false;
        }
    }
}
