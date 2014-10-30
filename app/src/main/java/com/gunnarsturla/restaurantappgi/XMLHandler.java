package com.gunnarsturla.restaurantappgi;
import android.util.Log;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.List;

/**
 * @author Þórhildur Magnúsdóttir
 * @since 12.10.14
 * Þessi klasi bregst við gögnum úr XML skránni og býr til hluti
 */
public class XMLHandler extends DefaultHandler {
    // List to hold all menu items fro xml file
    private List<Item> items = null;
    private Item item = null;

    public List<Item> getItems() {
        return items;
    }

    // boolean variables for all Item variables
    boolean bId = false;
    boolean bPrice = false;
    boolean bCals = false;
    boolean bName = false;
    boolean bDesc = false;
    boolean bIngr = false;
    boolean bImgh = false;

    /*  Set each boolean variable as true when an element is started to tell the handler that he
     *  can set the appropriate Item attribute (in method characters()).
     */

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException{
        Log.i("START", String.format("Start element: %s", qName));
        if (qName.equalsIgnoreCase("item")){
//            String sId = attributes.getValue("id");
//            int id = Integer.getInteger(sId);
//            item = new Item(0);
            // TODO create special method for submenus
        }
        else if (qName.equalsIgnoreCase("name")){
            bName = true;
        }
        else if (qName.equalsIgnoreCase("price")){
            bPrice = true;
        }
        else if (qName.equalsIgnoreCase("calories")){
            bCals = true;
        }
        else if (qName.equalsIgnoreCase("description")){
            bDesc = true;
        }
        else if (qName.equalsIgnoreCase("ingredients")){
            bIngr = true;
        }
        else if (qName.equalsIgnoreCase("name")){
            bImgh = true;
        }
    }
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException{
        System.out.println("End element: " + qName);
        if (qName.equalsIgnoreCase("item")){
            items.add(item);
        }
        // TODO add items to different submenus accordingly
    }
    @Override
    public void characters(char ch[], int start, int length) throws SAXException{
        if (bPrice) {
            item.setPrice(Integer.parseInt(new String(ch, start, length)));
            bPrice = false;
        }
        // TODO create else if for all other variables
    }
}
