package com.example.calorie1;

import org.json.JSONArray;
import org.json.JSONObject;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

/**
 * use the foursquare API to search
 *
 * @author Rongzhi Wang
 * @version 1.1
 */
public class FoursquareAPI {
    private static final String API_KEY = "OS5M33HTJRD20W4RWJUAHYWH5KAREPPN1CKTPVVQQU2JENJZ";
    private static final String SEARCH_ID_cx = "I10S2OCFJL0VBK0PAR3JOUZG4D2GD1BABKZFIHFSMX2YU5WJ";

    public static String mapDisplay(String[] params, String[] values) {
        URL url = null;
        HttpURLConnection connection = null;
        String textResult = "";
        String query_parameter="";
        if (params!=null && values!=null){
            for (int i =0; i < params.length; i ++){
                query_parameter += "&";
                query_parameter += params[i];
                query_parameter += "=";
                query_parameter += values[i];
            }
        }
        try {
            url = new URL("https://api.foursquare.com/v2/venues/search/?v=20190517&client_id="
                    + SEARCH_ID_cx+ "&client_secret="+ API_KEY + query_parameter);
            connection = (HttpURLConnection)url.openConnection();
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            Scanner scanner = new Scanner(connection.getInputStream());
            while (scanner.hasNextLine()) {
                textResult += scanner.nextLine();
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            connection.disconnect();
        }
        return textResult;
    }

    /**
     * get the nearest park information
     * @param result string data contains the park information
     * @return an array contains the park information
     */
    public static String[] getUserMap(String result){
        String[] park = new String[3];
        String name =null;
        String lat = null;
        String lng = null;
        try{
            JSONObject jsonObject = new JSONObject(result).getJSONObject("response");
            JSONArray jsonArray = jsonObject.getJSONArray("venues");
            JSONObject jsonObject2 = jsonArray.getJSONObject(0).getJSONObject("location");
            if(jsonArray != null && jsonArray.length() > 0) {
                name = jsonArray.getJSONObject(0).getString("name");
                lat = jsonObject2.getString("lat");
                lng = jsonObject2.getString("lng");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        park[0]=name;
        park[1]=lat;
        park[2]=lng;
        return park;
    }
}


