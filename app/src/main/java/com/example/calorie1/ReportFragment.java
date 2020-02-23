package com.example.calorie1;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.ColumnChartView;
import lecho.lib.hellocharts.view.PieChartView;

/**
 * create a report screen that contains a pie chart and column chart to show the value
 *
 * @author Rongzhi Wang
 * @version 1.1
 */
public class ReportFragment extends Fragment {
    View vReport;
    PieChartView pieChartView;
    ColumnChartView columnChartView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Reports");
        vReport = inflater.inflate(R.layout.fragment_reports, container, false);
        pieChartView = vReport.findViewById(R.id.pieChart);
        columnChartView = vReport.findViewById(R.id.barChart);
        SharedPreferences spMyusers = getActivity().getSharedPreferences("myUsers"
                , Context.MODE_PRIVATE);
        String totalCaloriesConsumed = spMyusers.getString("totalCaloriesConsumed",null);
        List<SliceValue> pieData = new ArrayList<>();
        pieData.add(new SliceValue(30, Color.parseColor("#7CDBD5")).setLabel("Total calories consumed"));
        pieData.add(new SliceValue(25, Color.parseColor("#F53240")).setLabel("total calories burned"));
        pieData.add(new SliceValue(10, Color.parseColor("#F9BE02")).setLabel("remaining calories"));
        PieChartData pieChartData = new PieChartData(pieData);
        pieChartData.setHasLabels(true).setValueLabelTextSize(14);
        pieChartView.setPieChartData(pieChartData);
        return vReport;
    }

    /**
     * get remaining calories on specific date from the server
     */
    private class GetRemainingCaloriesAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            return RestClient.findRemaingCalories(params[0],params[1]);
        }
        @Override
        protected void onPostExecute(String message) {
        }
    }

    /**
     * get information during the specific period time
     */
    private class GetPeriodTimeAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            return RestClient.findPeriodTime(params[0],params[1],params[2]);
        }
        @Override
        protected void onPostExecute(String message) {

        }
    }
}
