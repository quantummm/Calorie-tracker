package com.example.calorie1;

import android.util.Log;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * this class is used to connect the server and client
 * HttpURLConnection method is used to invoke method from the server
 *
 * @author Rongzhi Wang
 *  * @version 1.1
 */
public class RestClient extends MainActivity {
    private static final String BASE_URL =
                "http://118.138.78.101:8080/Cal4/webresources/";

    /**
     * get a user's credential information by login username
     *
     * @param username login username
     * @return JSON object contains a user's credential information in string type
     */
    public static String findByUserName(String username) {
        final String methodPath = "restcal4.credential/" + username;
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
        try {
            url = new URL(BASE_URL + methodPath);
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            Scanner inStream = new Scanner(conn.getInputStream());
            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return textResult;
    }

    /**
     * get user's firstname from the result in JSON object of string type
     *
     * @param result a string contains user's information
     * @return user's firstname
     */
    public static String getFirstname(String result){
        String firstName = null;
        try{
            JSONObject jsonObject = new JSONObject(result).getJSONObject("userid");
            firstName = jsonObject.getString("name");
        }catch (Exception e){
            e.printStackTrace();
            firstName = "NO INFO FOUND";
        }
        return firstName;
    }

    /**
     * get user's information and store in a map
     *
     * @param result a string contains user's infomation
     * @return a map contains userid,password and username
     */
    public static Map<String,String> getUserInfo(String result){
        String name = null;
        String password = null;
        int id = 0;
        HashMap<String,String> map = new HashMap<>();
        try{
            JSONObject jsonObject = new JSONObject(result);
            name = jsonObject.getString("username");
            password = jsonObject.getString("passwordhash");
            map.put("username",name);
            map.put("passwordhash",password);
            map.put("userid",String.valueOf(id));
        }catch (Exception e){
            e.printStackTrace();
             name = "NO INFO FOUND";
        }
        return map;
    }

    /**
     * get the user's password from the server
     *
     * @param result a string contains user's information
     * @return user's password in string type
     */
    public static String getPassword(String result){
        String password = null;
        try{
            JSONObject jsonObject = new JSONObject(result);
            password = jsonObject.getString("passwordhash");
        }catch (Exception e){
            e.printStackTrace();
            password = "NO INFO FOUND";
        }
        return password;
    }

    /**
     * post a credential entity to the server
     *
     * @param credential contains a user's credential information
     */
    public static void createNewCredential(Credential credential){
        URL url = null;
        HttpURLConnection conn = null;
        final String methodPath="restcal4.credential/";
        try {
            Gson gson =new Gson();
            String stringCrendentialJson=gson.toJson(credential);
            url = new URL(BASE_URL + methodPath);
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setFixedLengthStreamingMode(stringCrendentialJson.getBytes().length);
            conn.setRequestProperty("Content-Type", "application/json");
            PrintWriter out= new PrintWriter(conn.getOutputStream());
            out.print(stringCrendentialJson);
            out.close();
            Log.i("error",new Integer(conn.getResponseCode()).toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
    }

    /**
     * post a enduser entity to the server
     *
     * @param enduser a class contains a user's information
     */
    public static void createNewEnduser(Enduser enduser){
        URL url = null;
        HttpURLConnection conn = null;
        final String methodPath="restcal4.enduser/";
        try {
            Gson gson =new Gson();
            String stringCrendentialJson=gson.toJson(enduser);
            url = new URL(BASE_URL + methodPath);
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setFixedLengthStreamingMode(stringCrendentialJson.getBytes().length);
            conn.setRequestProperty("Content-Type", "application/json");
            PrintWriter out= new PrintWriter(conn.getOutputStream());
            out.print(stringCrendentialJson);
            out.close();
            Log.i("error",new Integer(conn.getResponseCode()).toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
    }

    /**
     * get a user's information from enduser entity
     *
     * @param firstname user's firstname
     * @return the user's information from the server in string type
     */
        public static String getEnduser(String firstname) {
            final String methodPath = "restcal4.enduser/findByName/"+ firstname;
            URL url = null;
            HttpURLConnection conn = null;
            String textResult = "";
            try {
                url = new URL(BASE_URL + methodPath);
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("Accept", "application/json");
                Scanner inStream = new Scanner(conn.getInputStream());
                while (inStream.hasNextLine()) {
                    textResult += inStream.nextLine();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                conn.disconnect();
            }
            return textResult;
        }

    /**
     * get userid from the raw data
     *
     * @param result raw data contains the user's information
     * @return a user's userid
     */
        public static String getUserId(String result){
            String id = null;
            try{
                JSONArray jsonArray = new JSONArray(result);
                id = jsonArray.getJSONObject(0).getString("userid");
            }catch (Exception e){
                e.printStackTrace();
                id = "NO INFO FOUND";
            }
            return id;
        }

    /**
     * get user's address
     *
     * @param result string value contains a user's information
     * @return a user's address
     */
        public static String getUserAddress(String result){
            String address = null;
            try {
                JSONArray jsonArray = new JSONArray(result);
                JSONObject jsonObject = jsonArray.getJSONObject(0);
                address= jsonObject.getString("address");
            }
            catch (Exception e){
                e.printStackTrace();
                address = "NO INFO FOUND";
            }


            return address;
        }

    /**
     * post a consumption table to the server
     *
     * @param consumption consumption entity contains a user's consumption record
     */
        public static void createConsumption(Consumption consumption){
            //initialise
            URL url = null;
            HttpURLConnection conn = null;
            final String methodPath="restcal4.consumption/";
            try {
                Gson gson =new Gson();
                String stringCrendentialJson=gson.toJson(consumption);
                url = new URL(BASE_URL + methodPath);
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("PUT");
                conn.setDoOutput(true);
                conn.setFixedLengthStreamingMode(stringCrendentialJson.getBytes().length);
                conn.setRequestProperty("Content-Type", "application/json");
                PrintWriter out= new PrintWriter(conn.getOutputStream());
                out.print(stringCrendentialJson);
                out.close();
                Log.i("error",new Integer(conn.getResponseCode()).toString());
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                conn.disconnect();
            }
        }

    /**
     * get all users from the server enduser entity
     *
     * @return all users' information in string type
     */
        public static String findAllUser() {
            final String methodPath = "restcal4.enduser";
            URL url = null;
            HttpURLConnection conn = null;
            String textResult = "";
            try {
                url = new URL(BASE_URL + methodPath);
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("Accept", "application/json");
                Scanner inStream = new Scanner(conn.getInputStream());
                while (inStream.hasNextLine()) {
                    textResult += inStream.nextLine();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                conn.disconnect();
            }
            return textResult;
        }

    /**
     * get all users' email from the enduser entity in the server
     *
     * @param result all users' information in string type
     * @return an arraylist contains all emails
     */
        public static ArrayList<String> getAllEmail(String result){
            String error = null;
            String email = null;
            ArrayList<String> emailArray = new ArrayList<>();
            try {
                JSONArray jsonArray = new JSONArray(result);
                for (int i = 0; i <= emailArray.size(); i++) {
                    JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                    email = jsonObject2.getString("email");
                    emailArray.add(email);
                }
            }catch (JSONException e){
                    e.printStackTrace();
                email = "No INFO FOUND";
                }
            return emailArray;
        }

    /**
     * get the number of all users in the server
     * @return the number of all users in string type
     */
        public static String countEnduser() {
            final String methodPath = "restcal4.enduser/count";
            URL url = null;
            HttpURLConnection conn = null;
            String textResult = "";
            try {
                url = new URL(BASE_URL + methodPath);
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Content-Type", "text/plain");
                conn.setRequestProperty("Accept", "text/plain");
                Scanner inStream = new Scanner(conn.getInputStream());
                while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                conn.disconnect();
            }
            return textResult;
        }

    /**
     * get a user's credential entity by userid
     *
     * @param userid an integer that contains a user's userid
     * @return a user's credential entity in string type
     */
        public static String findByUserId(int userid) {
            final String methodPath = "restcal4.credential/findByUserid/" + userid;
            URL url = null;
            HttpURLConnection conn = null;
            String textResult = "";
            try {
                url = new URL(BASE_URL + methodPath);
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("Accept", "application/json");
                Scanner inStream = new Scanner(conn.getInputStream());
                while (inStream.hasNextLine()) {
                    textResult += inStream.nextLine();
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                conn.disconnect();
            }
            return textResult;
        }

    /**
     * get the food entity by food category
     *
     * @param category a kind of food category
     * @return information about food in same category
     */
        public static String findByCategory(String category) {
            final String methodPath = "restcal4.food/findByCategory/" + category;
            URL url = null;
            HttpURLConnection conn = null;
            String textResult = "";
            try {
                url = new URL(BASE_URL + methodPath);
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("Accept", "application/json");
                Scanner inStream = new Scanner(conn.getInputStream());
                while (inStream.hasNextLine()) {
                    textResult += inStream.nextLine();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                conn.disconnect();
            }
            return textResult;
        }

    /**
     * get a list of food name
     *
     * @param result string type value contains food information
     * @return an arraylist contains the food name
     */
        public static ArrayList<String> getFoodItem(String result){
            String error = null;
            String foodName = null;
            ArrayList<String> foodArray = new ArrayList<>();
            try{
                for( int i=0;i<=foodArray.size();i++){
                    JSONArray jsonArray = new JSONArray(result);
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    foodName = jsonObject.getString("foodname");
                    foodArray.add(foodName);
                }
            }catch (Exception e){
                e.printStackTrace();
                error = "NO INFO FOUND";
            }
            return foodArray;
        }

    /**
     * get the total calories consumed
     *
     * @param userid a user's userid
     * @param consumptionDate a user's consumptiondate
     * @return total calories consumed value in string type
     */
        public static String findTotalCaloriesConsumed(String userid, String consumptionDate) {
            final String methodPath = "restcal4.consumption/calories4d/" + userid + "/"
                    + consumptionDate;
            URL url = null;
            HttpURLConnection conn = null;
            String textResult = "";
            try {
                url = new URL(BASE_URL + methodPath);
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("Accept", "application/json");
                Scanner inStream = new Scanner(conn.getInputStream());
                while (inStream.hasNextLine()) {
                    textResult += inStream.nextLine();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                conn.disconnect();
            }
            return textResult;
        }

    /**
     * get remaining calories for a user on specific day
     *
     * @param userid a user's userid
     * @param date for which day that need to get the remaining calories
     * @return remaining calories value
     */
        public static String findRemaingCalories(String userid, String date) {
            final String methodPath = "restcal4.report/remainingCalories/" + userid + "/"
                    + date;
            URL url = null;
            HttpURLConnection conn = null;
            String textResult = "";
            try {
                url = new URL(BASE_URL + methodPath);
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("Accept", "application/json");
                Scanner inStream = new Scanner(conn.getInputStream());
                while (inStream.hasNextLine()) {
                    textResult += inStream.nextLine();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                conn.disconnect();
            }
            return textResult;
        }

    /**
     * get values during period time for a user
     *
     * @param userid a user's userid
     * @param startDate select the start date
     * @param endDate the end date
     * @return a string value contains data in this period time
     */
        public static String findPeriodTime(String userid, String startDate,String endDate) {
            final String methodPath = "restcal4.report/periodTime/" + userid + "/"
                    + startDate + endDate;
            URL url = null;
            HttpURLConnection conn = null;
            String textResult = "";
            try {
                url = new URL(BASE_URL + methodPath);
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("Accept", "application/json");
                Scanner inStream = new Scanner(conn.getInputStream());
                while (inStream.hasNextLine()) {
                    textResult += inStream.nextLine();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                conn.disconnect();
            }
            return textResult;
        }

    /**
     * get total calories burned from the server
     *
     * @param userid a user's userid
     * @return the user's calories burned in string type
     */
        public static String findTotalCaloriesBurned(String userid) {
            final String methodPath = "restcal4.enduser/totalCaloriesBurned/" + userid ;
            URL url = null;
            HttpURLConnection conn = null;
            String textResult = "";
            try {
                url = new URL(BASE_URL + methodPath);
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("Accept", "application/json");
                Scanner inStream = new Scanner(conn.getInputStream());
                while (inStream.hasNextLine()) {
                    textResult += inStream.nextLine();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                conn.disconnect();
            }
            return textResult;
        }

    /**
     * get the BMR value from the server
     *
     * @param userid a user's userid
     * @returna a string contains bmr value
     */
        public static String findBMRValue(String userid) {
            final String methodPath = "restcal4.enduser/BMR/" + userid ;
            URL url = null;
            HttpURLConnection conn = null;
            String textResult = "";
            try {
                url = new URL(BASE_URL + methodPath);
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("Accept", "application/json");
                Scanner inStream = new Scanner(conn.getInputStream());
                while (inStream.hasNextLine()) {
                    textResult += inStream.nextLine();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                conn.disconnect();
            }
            return textResult;
        }

    /**post a food table to the server
     *
     * @param food food table contains food information
     */
    public static void createFood(Food food){
        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        final String methodPath="restcal4.food/";
        try {
            Gson gson =new Gson();
            String stringCrendentialJson=gson.toJson(food);
            url = new URL(BASE_URL + methodPath);
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("PUT");
            conn.setDoOutput(true);
            conn.setFixedLengthStreamingMode(stringCrendentialJson.getBytes().length);
            conn.setRequestProperty("Content-Type", "application/json");
            PrintWriter out= new PrintWriter(conn.getOutputStream());
            out.print(stringCrendentialJson);
            out.close();
            Log.i("error",new Integer(conn.getResponseCode()).toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
    }
}

