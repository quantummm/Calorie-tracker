package com.example.calorie1;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Manage the daily diet screen, create daily diet view and
 * display the image, description, nutrition information, category
 * and name about food
 *
 * @author Rongzhi Wang
 * @version 1.1
 */
public class DailyDietFragment extends Fragment {
    View vDisplayDailyDiet;
    private ArrayAdapter<String> foodAdapter;
    private String category,fooditem;
    private EditText editFood;
    private Button btnAddNewFood;
    private ImageView foodImage;
    private ArrayList<String> foodValues;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Daile Diet");
        vDisplayDailyDiet = inflater.inflate(R.layout.fragment_daily_diet, container
                , false);
        editFood = (EditText)vDisplayDailyDiet.findViewById(R.id.editNewFood);
        foodImage = (ImageView)vDisplayDailyDiet.findViewById(R.id.imageGoogleSearch);
        btnAddNewFood = (Button)vDisplayDailyDiet.findViewById(R.id.btnAddFood);
        btnAddNewFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String keyword = editFood.getText().toString();
                SearchGoogleAsyncTask searchGoogleAsyncTask=new SearchGoogleAsyncTask();
                DownloadImageFromInternet imageFromInternet = new DownloadImageFromInternet();
                searchGoogleAsyncTask.execute(keyword);
                imageFromInternet.execute(keyword);
                SearchFoodDatabaseAsyncTask searchFoodDatabaseAsyncTask = new
                        SearchFoodDatabaseAsyncTask();
                searchFoodDatabaseAsyncTask.execute(keyword);
            }
        });
        final Spinner sFoodItem = (Spinner)vDisplayDailyDiet.findViewById(R.id.spinnerItem);
        foodAdapter = new ArrayAdapter<String>(this.getActivity(),
                android.R.layout.simple_spinner_item, new ArrayList<String>());
        String[] categoryValues = {"vegetable","seafood","drink","bread","meat",
                "fruit","grain","nut"};
        final Spinner sFoodCategory = (Spinner) vDisplayDailyDiet.findViewById(R.id.spinnerCategory);
        final ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this.getActivity(),
                android.R.layout.simple_spinner_item, categoryValues);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sFoodCategory.setAdapter(spinnerAdapter);
        sFoodCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                foodAdapter.clear();
                String selectCategory = parent.getItemAtPosition(position).toString();
                if (selectCategory != null) {
                    category = selectCategory;
                }
                GetFoodItemByCategoryAsyncTask getFoodItemByCategory = new GetFoodItemByCategoryAsyncTask();
                getFoodItemByCategory.execute(category);
                foodAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                sFoodItem.setAdapter(foodAdapter);
                sFoodItem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position,
                                               long id) {
                        String selectFood = parent.getItemAtPosition(position).toString();
                        if (selectFood != null) {
                            fooditem = selectFood;
                        }
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        return vDisplayDailyDiet;
    }

    /**
     * Using the Google custom search API to get the food description information
     */
    private class SearchGoogleAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            return SearchGoogleAPI.search(params[0], new String[]{"num"}, new String[]{"1"});
        }
        @Override
        protected void onPostExecute(String result) {
            TextView foodDesc= (TextView) vDisplayDailyDiet.findViewById(R.id.textDesc);
            foodDesc.setText(SearchGoogleAPI.getSnippet(result));
        }
    }

    /**
     * Using the Google custom search API to get the image of the food
     */
    private class DownloadImageFromInternet extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... params) {
            String result = SearchGoogleAPI.search(params[0], new String[]{"num"}, new
                    String[]{"1"});
            String imageURL = SearchGoogleAPI.getImage(result);
            Bitmap bimage = null;
            try {
                InputStream in = new java.net.URL(imageURL).openStream();
                bimage = BitmapFactory.decodeStream(in);

            } catch (Exception e) {
                Log.e("Error Message", e.getMessage());
                e.printStackTrace();
            }
            return bimage;
        }
        @Override
        protected void onPostExecute(Bitmap result) {
            foodImage.setImageBitmap(result);
        }
    }

    /**
     * Using the Food Database API to get the fat and energy in kcal of the food
     */
    private class SearchFoodDatabaseAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            return FoodDatabaseAPI.search(params[0], new String[]{"num"}, new String[]{"1"});
        }
        @Override
        protected void onPostExecute(String result) {
            String[] foodInfo = new String[2];
            foodInfo=FoodDatabaseAPI.getFoodInfo(result);
            TextView textFat= (TextView) vDisplayDailyDiet.findViewById(R.id.textFat);
            TextView textEnergy= (TextView) vDisplayDailyDiet.findViewById(R.id.textEnergy);
            textFat.setText("Fat: "+ foodInfo[0]);
            textEnergy.setText("Energy: "+ foodInfo[1]);
        }
    }

    /**
     * Access data and execute query from the server-side to get the food name in food table
     * and applied to the spinner for selecting the food item
     */
    private class GetFoodItemByCategoryAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            foodValues = RestClient.getFoodItem(RestClient.findByCategory(params[0]));
            return RestClient.findByCategory(params[0]);
        }
        @Override
        protected void onPostExecute(String message) {
            foodValues = RestClient.getFoodItem(message);
            for (String i : foodValues){
                foodAdapter.add(i);
            }
        }
    }
}
