/*
package com.company;

import com.google.gson.Gson;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

*/
/**
 * Created by jean on 8/17/2016.
 *//*

public class scetch {

    Routes[] data = null;

    JSONParser parser = new JSONParser();

    try {
        Object obj = null;
        try {
            obj = parser.parse(new FileReader("D:/routes_august.json"));
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (ParseException e1) {
            e1.printStackTrace();
        }
        JSONArray jsonArray = (JSONArray) obj;
        JSONArray resultArray = new JSONArray();
        JSONObject currentObject  = new JSONObject();

        Routes[] array;

        Gson gson = new Gson();

        try {
            array = gson.fromJson(new FileReader("D:/routes_august.json"), Routes[].class);
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }


        JSONArray routesJsonArray = new JSONArray();
        JSONObject firstJsonObject = new JSONObject();

        for (int i = 0; i < jsonArray.size()  - 1; i++) {

            firstJsonObject.put("routeNumber", array[i].getRouteNumber());

            if (firstJsonObject.get("routeNumber").equals(array[i + 1].getRouteNumber())) {
                JSONArray trackJsonObject = new JSONArray();
                JSONArray track =new JSONArray();
                JSONObject latlon = new JSONObject();

                for (int j = i; j < array.length  - 1; j++) {
//                    String routeNumberNext = array[j + 1].getRouteNumber();
                    latlon.put("lat", array[j + 1].getLatitude());
                    latlon.put("lon", array[j + 1].getLongitude());
                    trackJsonObject.add(latlon);

                }
                track.add(trackJsonObject);

                firstJsonObject.put("track", track);

            } else {
                routesJsonArray.add(firstJsonObject);
                continue;
            }

        }

        System.out.print(routesJsonArray.toJSONString());

    } catch (IOException e) {
        e.printStackTrace();
    } catch (ParseException e) {
        e.printStackTrace();
    }
}
*/
