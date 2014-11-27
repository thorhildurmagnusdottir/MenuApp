package data;


/**
 * @author Þórhildur Magnúsdóttir
 * @since 12.10.14
 * This class communicates with the web service to retreive the xml file
 */

import android.os.Environment;
import android.util.Log;

import com.gunnarsturla.menuapp.Constants;

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
    public static Vector<SubMenu> totalMenu;

    public Vector<SubMenu> populateMenu(){
        return this.totalMenu;
    }

    protected XMLParser(XMLHandler handler){
        this.handler = handler;
    }

    public Void populateItems(){
        Log.i("ParseXML", "came here");
		// returns InputStream
        InputStream menuXML = null;
        File sdcard = Environment.getExternalStorageDirectory();
        File file = new File(sdcard, Constants.menuFile);
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