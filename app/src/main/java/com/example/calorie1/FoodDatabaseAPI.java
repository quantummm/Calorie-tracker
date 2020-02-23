package com.example.calorie1;

import org.json.JSONArray;
import org.json.JSONObject;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

/**
 * use the food database API to search food information
 *
 * @author Rongzhi Wang
 * @version 1.1
 */
public class FoodDatabaseAPI {
    private static final String API_KEY = "22b01e1b53c73d7340c41b2fcbdac84b";
    private static final String SEARCH_ID_cx = "0e5cb63b";

    /**
     * use HttPURLConnection to invoke food database api search food method
     *
     * @param keyword a food name
     * @param params an array that contains parameter name
     * @param values an array that contains the values for parameters
     * @return the JSON strings about a kind of food information from food database API
     */
    public static String search(String keyword, String[] params, String[] values) {
        keyword = keyword.replace(" ", "+");
        URL url = null;
        HttpURLConnection connection = null;
        String textResult = "";
        String query_parameter = "";
        if (params != null && values != null) {
            for (int i = 0; i < params.length; i++) {
                query_parameter += "&";
                query_parameter += params[i];
                query_parameter += "=";
                query_parameter += values[i];
            }
        }
        try {
            url = new URL("https://api.edamam.com/api/food-database/parser?ingr=" + keyword
                    + "&app_id=" + SEARCH_ID_cx + "&app_key=" + API_KEY + query_parameter);
            connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            Scanner scanner = new Scanner(connection.getInputStream());
            while (scanner.hasNextLine()) {
                textResult += scanner.nextLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.disconnect();
        }
        return textResult;
    }

    /**
     * extract the fat and energy information of food from the entire response in JSON
     * @param result string data in a valid JSON format
     * @return an array that contains the specific food information
     */
    public static String[] getFoodInfo(String result){
        String[] foodInfo = new String[2];
        String fat = null;
        String energy = null;
        try{
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("hints");
            JSONObject jsonObject2 = jsonArray.getJSONObject(0).getJSONObject("food").getJSONObject("nutrients");
            if(jsonArray != null && jsonArray.length() > 0) {
                energy =jsonObject2.getString("ENERC_KCAL");
                fat = jsonObject2.getString("FAT");
            }
        }catch (Exception e){
            e.printStackTrace();
            energy = "NO INFO FOUND";
            fat = "NO INFO FOUND";
        }
        foodInfo[0]=fat;
        foodInfo[1]=energy;
        return foodInfo;
    }

}
