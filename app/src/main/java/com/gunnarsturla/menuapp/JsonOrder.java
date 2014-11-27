package com.gunnarsturla.menuapp;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import menu.Item;
import menu.SubMenu;

/*
 * @author Dagny
 * @since 24.11.2014.
 * Skrifar JSONobject representasjón af pöntunarlistanum,
 * sem er tekinn in sem SubMenu af Itemum
 */
public class JsonOrder {

    public JSONObject OrderToJSon(SubMenu order) {
        try {
            JSONObject orderJSON = new JSONObject();
            JSONArray jsonArr = new JSONArray();
            for (Item i : order.getItems()){
                JSONObject itemObject = new JSONObject();
                itemObject.put("itemName", i.getName());
                itemObject.put("itemId", i.getId());
                itemObject.put("itemComment", i.getComment());
                Log.i("itemJSON", itemObject.toString());
                jsonArr.put(itemObject);
            }
            orderJSON.put("orderlist", jsonArr);
            return orderJSON;
        }

        catch(JSONException ex) {
            ex.printStackTrace();
        }

        return null;



    }

}
