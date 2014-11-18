package com.gunnarsturla.restaurantappgi;


/**
 * @author Þórhildur Magnúsdóttir
 * @since 12.10.14
 * This class communicates with the web service to update the xml file if
 * a new version is available.
 */

import android.os.Environment;
import android.util.Log;

import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Vector;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import menu.Item;
import menu.SubMenu;

public class XMLParser {

    public static InputStream xml;
    public static List<Item> items;
    public static XMLHandler handler;
    public static SubMenu subMenu;
    public static Vector<SubMenu> totalMenu;
    public Vector<SubMenu> populateMenu(){
        return this.totalMenu;
    }
    protected XMLParser(InputStream xml, XMLHandler handler){
        Log.i("XMLParser", "created one");
        this.xml = xml;
        this.items = null;
        this.handler = handler;
        this.subMenu = null;
        this.totalMenu = null;
    }
    protected XMLParser(XMLHandler handler){
        this.handler = handler;
    }
    public List<Item> parseXML() {
        Log.i("ParseXML", "came here");
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        try {
            SAXParser saxParser = saxParserFactory.newSAXParser();
//            XMLHandler handler = new XMLHandler();
            saxParser.parse(this.xml, handler);
            this.items = handler.getItems();
            this.totalMenu = handler.getMenu();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this.items;
    }
    public Void populateItems(){
        Log.i("ParseXML", "came here");
        InputStream menuXML = null;   // returns InputStream
        File sdcard = Environment.getExternalStorageDirectory();
        File file = new File(sdcard,Constants.menuFile);
        try {
            menuXML = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        try {
            SAXParser saxParser = saxParserFactory.newSAXParser();
            if (null != menuXML) saxParser.parse(menuXML, handler);
            this.items = handler.getItems();
            this.totalMenu = handler.getMenu();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}