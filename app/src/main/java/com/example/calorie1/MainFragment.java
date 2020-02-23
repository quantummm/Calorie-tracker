package com.example.calorie1;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;

/**
 * Main fragment of the program, displays the home page of the program,
 * contains the home screen and calorie track screen
 *
 * @author Rongzhi Wang
 * @version 1.1
 */
public class MainFragment extends Fragment implements View.OnClickListener{

    View vMain;
    private EditText editGoal;
    private TextView welcomeText,currentDate,goalView,consumedText;
    private Button resetGoal;
    private String name,userid,strDate,totalCaloriesConsumed;
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        vMain = inflater.inflate(R.layout.fragment_home_screen, container, false);
        welcomeText=(TextView)vMain.findViewById(R.id.welcome);
        currentDate=(TextView)vMain.findViewById(R.id.textCurrentDate);
        editGoal = (EditText)vMain.findViewById(R.id.editGoal);
        goalView=(TextView)vMain.findViewById(R.id.textSetGoal);
        resetGoal = (Button)vMain.findViewById(R.id.buttonResetGoal);
        consumedText = (TextView)vMain.findViewById(R.id.textCosumed);
        HomeScreen activity = (HomeScreen) getActivity();
        name = activity.getMyData();
        welcomeText.setText("Welcome " + name + "!");
        SharedPreferences spMyusers = getActivity().getSharedPreferences("myUsers", Context.MODE_PRIVATE);
        SharedPreferences.Editor eMyUsers = spMyusers.edit();
        eMyUsers.putString("firstName", name);
        eMyUsers.apply();
        GetUserIdAsyncTask getUserIdAsyncTask = new GetUserIdAsyncTask();
        try {
            userid = getUserIdAsyncTask.execute(name).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
        currentDate.setText(currentDateTimeString);
        resetGoal.setOnClickListener(this);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        strDate = format.format( new Date());
        return vMain;
    }

    /**
     * When the reset button is clicked at the home page, it will
     * display the information
     */
    public void onClick(View v) {
        String setGoal = editGoal.getText().toString();
        if (setGoal.isEmpty()) {
          editGoal.setError("Integer Goal is required!");
          return;
        }
        GetTotalCaloriesConsumedAsyncTask getTotalCaloriesConsumedAsyncTask = new
                GetTotalCaloriesConsumedAsyncTask();
        getTotalCaloriesConsumedAsyncTask.execute(userid,strDate);
        SharedPreferences spMyusers = getActivity().getSharedPreferences("myUsers", Context.MODE_PRIVATE);
        String myUserGoal=setGoal;
        SharedPreferences.Editor eMyUsers = spMyusers.edit();
        eMyUsers.putString("todayGoal", myUserGoal);
        eMyUsers.putString("totalCaloriesConsumed", totalCaloriesConsumed);
        eMyUsers.apply();
        String feedback = setGoal + " is today's goal.";
        goalView.setText(feedback);

    }

    /**
     * get the total calories consumed value from the server
     */
    private class GetTotalCaloriesConsumedAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            return RestClient.findTotalCaloriesConsumed(params[0],params[1]);
        }

        @Override
        protected void onPostExecute(String message) {
            totalCaloriesConsumed = null;
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(message);
                totalCaloriesConsumed = jsonObject.getString("totalConsumed");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            consumedText.setText("Today's total calories consumed is " + totalCaloriesConsumed);
        }
    }

    /**
     * get userid through user's firstname from the server
     */
    private class GetUserIdAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            userid = RestClient.getUserId(RestClient.getEnduser(params[0])) ;
            return userid;
        }

        @Override
        protected void onPostExecute(String message) {
        }
    }

    /**
     * get total calories burned from the server
     */
    private class GetTotalCaloriesBurnedAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            return RestClient.findTotalCaloriesBurned(params[0]);
        }
        @Override
        protected void onPostExecute(String message) {

        }
    }

    /**
     * get the BMR value from the server
     */
    private class GetBMRValueAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            return RestClient.findBMRValue(params[0]);
        }
        @Override
        protected void onPostExecute(String message) {

        }
    }

}