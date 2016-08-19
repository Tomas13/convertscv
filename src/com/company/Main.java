package com.company;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Main {


    public static void main(String[] args) throws FileNotFoundException {
        StringBuilder stringBuilde = new StringBuilder();
        try {

            for (String s : Files.readAllLines(Paths.get("./example.json"))) {
                stringBuilde.append(s);
            }

            JSONParser parser = new JSONParser();
            JSONArray json = (JSONArray) parser.parse(stringBuilde.toString());

            JSONArray resultJsonArray = Main.process(json);

            System.out.println("HEY : " + resultJsonArray);
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }


    public static JSONArray process(JSONArray data) {
        JSONArray resultJson = new JSONArray();

        try {
            JSONArray jsonArray = new JSONArray();
            jsonArray.addAll(data);

            JSONArray emptyJsonArray = new JSONArray();
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONArray trackJsonArray = new JSONArray();
                JSONArray busStopsJsonArray = new JSONArray();
                JSONObject newJson = new JSONObject();
                JSONObject firstJsonObject = (JSONObject) jsonArray.get(i);

                Double lat = (Double) firstJsonObject.get("latitude");
                Double lon = (Double) firstJsonObject.get("longitude");
                JSONObject firstTrackObject = new JSONObject();
                firstTrackObject.put("lat", lat);
                firstTrackObject.put("lon", lon);
                trackJsonArray.add(firstTrackObject);

                JSONObject stopsJsonObject = new JSONObject();

                //searching for busstops points
                if (firstJsonObject.get("isStopPoint").equals(1)) {
                    stopsJsonObject.put("id", firstJsonObject.get("stopID"));
                    stopsJsonObject.put("title", firstJsonObject.get("stopTitle"));
                    stopsJsonObject.put("x", firstJsonObject.get("stopLongitude"));
                    stopsJsonObject.put("y", firstJsonObject.get("stopLatitude"));
                    stopsJsonObject.put("desc", "");
                    stopsJsonObject.put("routes", emptyJsonArray);
                    busStopsJsonArray.add(stopsJsonObject);
                    newJson.put("busstops", busStopsJsonArray);
                }

                for (int j = i + 1; j < jsonArray.size(); j++) {

                    JSONObject secondJsonObject = (JSONObject) jsonArray.get(j);
                    if (firstJsonObject.get("routeNumber").equals(secondJsonObject.get("routeNumber"))) {
                        //if equal then create track array with lat lon

                        JSONObject secondTrackObject = new JSONObject();
                        Double lat2 = (Double) secondJsonObject.get("latitude");
                        Double lon2 = (Double) secondJsonObject.get("longitude");
                        secondTrackObject.put("lat", lat2);
                        secondTrackObject.put("lon", lon2);

                        trackJsonArray.add(secondTrackObject);

                        newJson.put("track", trackJsonArray);
                        newJson.put("n", firstJsonObject.get("routeNumber"));
                        newJson.put("dRu", "");
                        newJson.put("dFromRu", "");
                        newJson.put("dToRu", "");
                        newJson.put("id", firstJsonObject.get("id"));
                        newJson.put("d", "not used");
                        newJson.put("dKz", "");
                        newJson.put("descrRu", "");
                        newJson.put("descrKz", "");

                        JSONObject stopsJsonObject2 = new JSONObject();
                        if (secondJsonObject.get("isStopPoint").equals(1)) {
                            stopsJsonObject2.put("id", secondJsonObject.get("stopID"));
                            stopsJsonObject2.put("title", secondJsonObject.get("stopTitle"));
                            stopsJsonObject2.put("x", secondJsonObject.get("stopLongitude"));
                            stopsJsonObject2.put("y", secondJsonObject.get("stopLatitude"));
                            stopsJsonObject2.put("desc", "");
                            stopsJsonObject2.put("routes", emptyJsonArray);
                            busStopsJsonArray.add(stopsJsonObject2);
                            newJson.put("busstops", busStopsJsonArray);
                        }

                        i = j;

                    } else {
                        break;
                    }
                }
                resultJson.add(newJson);
            }
        } catch (Exception e) {

            e.printStackTrace();
        }

        return resultJson;
    }
}
