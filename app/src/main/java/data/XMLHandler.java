package data;

import android.util.Log;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

import menu.Item;
import menu.SubMenu;

/**
 * @author Þórhildur Magnúsdóttir
 * @since 12.10.14
 * Þessi klasi bregst við gögnum úr XML skránni og býr til hluti
 */
public class XMLHandler extends DefaultHandler {

    // boolean variables for all Item variables
    boolean bName = false;
    boolean bPrice = false;
    boolean bDesc = false;
    boolean bIngr = false;
    boolean bCals = false;
    boolean bImgh = false;
    boolean sName = false;
    boolean sImgh = false;
    boolean sPic = false; // submenupic
    boolean bThumbBig = false; // bigthumb

    // List to hold all menu items fro xml file
    private SubMenu subMenu;
    private List<Item> items;
    private Item currentItem;
    private ArrayList<SubMenu> allSubMenus;
    public XMLHandler() {
        Log.i("XMLHandler", "Created one");
        currentItem = new Item();
        items = new ArrayList<Item>();
        subMenu = new SubMenu("Submenu1", "");
        allSubMenus = new ArrayList<SubMenu>();
    }

    public List<Item> getItems() {
        return items;
    }

    public ArrayList<SubMenu> getMenu() { return allSubMenus; }

    /*  Set each boolean variable as true when an element is started to tell the handler that he
     *  can set the appropriate Item attribute (in method characters()).
     */
    @Override
    public void startElement(String uri, String localName, String eName, Attributes attributes) throws SAXException{
        if (eName.equalsIgnoreCase("submenu")){     subMenu = new SubMenu("submenuTest", ""); }
        else if (eName.equalsIgnoreCase("submenupic")){             sPic = true;         }
        else if (eName.equalsIgnoreCase("title")){                  sName = true;        }
        else if (eName.equalsIgnoreCase("imghash")){                sImgh = true;        }
        else if (eName.equalsIgnoreCase("name")){                   bName = true;        }
        else if (eName.equalsIgnoreCase("price")){                  bPrice = true;       }
        else if (eName.equalsIgnoreCase("description")){            bDesc = true;        }
        else if (eName.equalsIgnoreCase("ingredients")){            bIngr = true;        }
        else if (eName.equalsIgnoreCase("calories")){               bCals = true;        }
        else if (eName.equalsIgnoreCase("name")){                   bImgh = true;        }
        else if (eName.equalsIgnoreCase("bigthumb")){               bThumbBig = true;    }
        else if (eName.equalsIgnoreCase("item")){
            int id = Integer.parseInt(attributes.getValue("id"));
            Log.i("start element item", Integer.toString(id));
            currentItem = new Item(id);
            if (null == items){   items = new ArrayList<Item>();   }
        }
    }
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException{
        if (qName.equalsIgnoreCase("item")){
            items.add(currentItem);
            subMenu.addItem(currentItem);
        }
        else if (qName.equalsIgnoreCase("submenu")){   allSubMenus.add(subMenu);   }
    }
    @Override
    public void characters(char ch[], int start, int length) throws SAXException{

        if (sName) { subMenu.setName(new String(ch, start, length));
            sName = false; }
        else if (sImgh) { subMenu.setImghash(new String(ch, start, length));
            sImgh = false;  }
        else if (sPic) {subMenu.setPicture(new String(ch, start, length));
            sPic = false;    }
        else if (bName) { currentItem.setName(new String(ch, start, length));
            bName = false; }
        else if (bDesc) {   currentItem.setDescription(new String(ch, start, length));
            bDesc = false;}
        else if (bIngr) { currentItem.setIngredients(new String(ch, start, length));
            bIngr = false; }
        else if (bImgh) { currentItem.setImghash(new String(ch, start, length));
            bImgh = false; }
        else if (bCals) { currentItem.setCalories(Integer.parseInt(new String(ch, start, length)));
            bCals = false; }
        else if (bPrice) { currentItem.setPrice(Integer.parseInt(new String(ch, start, length)));
            bPrice = false; }
        else if (bThumbBig) {
            String thumbUrl = new String(ch, start, length);
            Log.i("thumburl is ", thumbUrl);
            currentItem.setThumbBigUrl(thumbUrl);
            bThumbBig = false; }
    }
}
