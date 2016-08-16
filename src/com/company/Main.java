package com.company;


import java.io.*;
import java.util.Iterator;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import com.oracle.javafx.jmx.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Main {



    Routes[] processedArray;

    JsonReader jsonReader;

    public static void main(String[] args) throws FileNotFoundException {
        // write your code here

        Routes[] data = null;

        JSONParser parser = new JSONParser();

      /*  try {

            Object obj = parser.parse(new FileReader("c:\\test.json"));

            JSONObject jsonObject = (JSONObject) obj;

            String name = (String) jsonObject.get("name");
            System.out.println(name);

            long age = (Long) jsonObject.get("age");
            System.out.println(age);

            // loop array
            JSONArray msg = (JSONArray) jsonObject.get("messages");
            Iterator<String> iterator = msg.iterator();
            while (iterator.hasNext()) {
                System.out.println(iterator.next());
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        */

        try {
            Object obj = parser.parse(new FileReader("D:/routes_august.json"));
            JSONArray jsonArray = (JSONArray) obj;
            JSONArray resultArray = new JSONArray();
            JSONObject currentObject  = new JSONObject();

            Routes[] array;

            Gson gson = new Gson();

            array = gson.fromJson(new FileReader("D:/routes_august.json"), Routes[].class);


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


    /**
     * Merge "source" into "target". If fields have equal name, merge them recursively.
     * @return the merged object (target).
     */
    public static JSONObject deepMerge(JSONObject source, JSONObject target) throws JSONException {
        for (Object key: source.keySet()) {
            Object value = source.get(key);
            if (!target.containsKey(key)) {
                // new value for "key":
                target.put(key, value);
            } else {
                // existing value for "key" - recursively deep merge:
                if (value instanceof JSONObject) {
                    JSONObject valueJson = (JSONObject)value;
                    deepMerge(valueJson, (JSONObject) target.get(key));
                } else {
                    target.put(key, value);
                }
            }
        }
        return target;
    }



}
