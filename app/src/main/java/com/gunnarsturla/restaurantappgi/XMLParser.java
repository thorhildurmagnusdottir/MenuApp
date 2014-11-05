package com.gunnarsturla.restaurantappgi;


/**
 * @author Þórhildur Magnúsdóttir
 * @since 12.10.14
 * This class communicates with the web service to update the xml file if
 * a new version is available.
 */

import android.util.Log;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Vector;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class XMLParser {

    public InputStream xml;
    public List<Item> items;
    public XMLHandler handler;
    public SubMenu subMenu;
    public Vector<SubMenu> totalMenu;
    public Vector<SubMenu> populateMenu(){
        return this.totalMenu;
    }
    public  XMLParser(InputStream xml, XMLHandler handler){
        Log.i("XMLParser", "created one");
        this.xml = xml;
        this.items = null;
        this.handler = handler;
        this.subMenu = null;
        this.totalMenu = null;
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
//    }
//    public Vector<SubMenu> parseForMenuXML() {
//        Log.i("ParseXML", "came here");
//        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
//        try {
//            SAXParser saxParser = saxParserFactory.newSAXParser();
////            XMLHandler handler = new XMLHandler();
//            saxParser.parse(this.xml, handler);
//            this.items = handler.getItems();
//            this.totalMenu = handler.getMenu();
//        } catch (SAXException | ParserConfigurationException | IOException e) {
//            e.printStackTrace();
//        }
//        return this.totalMenu;
    }
}