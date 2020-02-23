package com.example.calorie1;

import org.json.JSONArray;
import org.json.JSONObject;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

/**
 * use the google map API and google custom search api to search information
 *
 * @author Rongzhi Wang
 * @version 1.1
 */
public class SearchGoogleAPI {
    private static final String API_KEY = "AIzaSyDoGhQsBCq3vhINJoWE6Eu2RpVAwstb4Z4";
    private static final String SEARCH_ID_cx = "015583993923330876446:ruwpx6jfxck";
    public static String search(String keyword, String[] params, String[] values) {
        keyword = keyword.replace(" ", "+");
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
            url = new URL("https://www.googleapis.com/customsearch/v1?key="+
                    API_KEY+ "&cx="+ SEARCH_ID_cx + "&q="+ keyword + query_parameter);
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
     * get food description from the internet
     *
     * @param result search result through google search in string type
     * @return food description in string type
     */
    public static String getSnippet(String result){
        String snippet = null;
        try{
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("items");
            if(jsonArray != null && jsonArray.length() > 0) {
                snippet =jsonArray.getJSONObject(0).getString("snippet");
            }
        }catch (Exception e){
            e.printStackTrace();
            snippet = "NO INFO FOUND";
        }
        return snippet;
    }

    /**
     * get the image value from the internet
     *
     * @param result search result through google search in string type
     * @return food image imformation in string type
     */
    public static String getImage(String result){
        String src = null;
        try{
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("items");
            JSONObject jsonObject2 = jsonArray.getJSONObject(0).getJSONObject("pagemap");
            JSONArray jsonArray1 = jsonObject2.getJSONArray("cse_image");
            if(jsonArray1 != null && jsonArray1.length() > 0) {
                src= jsonArray1.getJSONObject(0).getString("src");
            }
        }catch (Exception e){
            e.printStackTrace();
            src = "NO INFO FOUND";
        }
        return src;
    }
}