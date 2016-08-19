package com.company;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
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

//            System.out.println(json);

            JSONArray resultJsonArray =   Main.process(json);

            System.out.println("HEY : " + resultJsonArray);
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }


    public static JSONArray process(JSONArray data){
        JSONArray resultJson = new JSONArray();

        try {
            JSONArray jsonArray = new JSONArray();
            jsonArray.addAll(data);

            for (int i = 0; i < jsonArray.size(); i++) {
                JSONArray trackJsonArray = new JSONArray();
                JSONObject newJson = new JSONObject();
                JSONObject firstJsonObject = (JSONObject) jsonArray.get(i);

                Double lat = (Double) firstJsonObject.get("latitude");
                Double lon = (Double) firstJsonObject.get("longitude");
                JSONObject firstTrackObject = new JSONObject();
                firstTrackObject.put("lat", lat);
                firstTrackObject.put("lon", lon);
                trackJsonArray.add(firstTrackObject);

                for (int j = i + 1; j < jsonArray.size(); j++) {

                    JSONObject secondJsonObject = (JSONObject) jsonArray.get(j);
                    if (firstJsonObject.get("routeNumber").equals(secondJsonObject.get("routeNumber"))){
                        //if equal then create track array with lat lon

                        JSONObject secondTrackObject = new JSONObject();
                        Double lat2 = (Double) secondJsonObject.get("latitude");
                        Double lon2 = (Double) secondJsonObject.get("longitude");
                        secondTrackObject.put("lat", lat2);
                        secondTrackObject.put("lon", lon2);

                        trackJsonArray.add(secondTrackObject);

                        newJson.put("track", trackJsonArray);
                        newJson.put("routeNumber", firstJsonObject.get("routeNumber"));
                        newJson.put("id", firstJsonObject.get("id"));
                        i = j;
                    }else{
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
