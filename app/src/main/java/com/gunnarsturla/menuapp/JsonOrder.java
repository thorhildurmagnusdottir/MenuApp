package com.gunnarsturla.menuapp;

import menu.Order;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Dagny on 24.11.2014.
 */
public class JsonOrder{

    public static String toJSon() {

        try {

            JSONObject jsonObj = new JSONObject();

            JSONArray jsonArr = new JSONArray();

            for(int i = 0; i < Order.size(); i++) {
                JSONObject order = new JSONObject();
                order.put("order", Order.get(i).getName());
                jsonArr.put(order);
            }

            jsonObj.put("orderlist", jsonArr);

            return jsonObj.toString();

        }

        catch(JSONException ex) {
            ex.printStackTrace();
        }

        return null;



    }

}
