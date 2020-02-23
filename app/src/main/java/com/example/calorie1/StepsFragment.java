package com.example.calorie1;

import android.arch.persistence.room.Room;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;

/**
 * this class creates the steps screen and allows users to insert,update,
 * read,delete the steps entered by themselves
 *
 * @author Rongzhi Wang
 * @version 1.1
 */
public class StepsFragment extends Fragment {
    View vSteps;
    StepsDatabase db = null;
    EditText editText = null;
    TextView textView_insert = null;
    TextView textView_read = null;
    TextView textView_delete = null;
    TextView textView_update = null;
    TextView textView_total = null;
    String totalSteps;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Steps");
        vSteps = inflater.inflate(R.layout.fragment_steps, container, false);
        db = Room.databaseBuilder(this.getActivity().getApplicationContext(),
                StepsDatabase.class, "SptesDatabase")
                .fallbackToDestructiveMigration()
                .build();
        Button addButton = (Button) vSteps.findViewById(R.id.addButton);
        editText = (EditText) vSteps.findViewById(R.id.editText);
        textView_insert = (TextView) vSteps.findViewById(R.id.textView);
        Button readButton = (Button) vSteps.findViewById(R.id.readButton);
        textView_read = (TextView) vSteps.findViewById(R.id.textView_read);
        Button deleteButton = (Button) vSteps.findViewById(R.id.deleteButton);
        textView_delete = (TextView) vSteps.findViewById(R.id.textView_delete);
        Button updateButton = (Button) vSteps.findViewById(R.id.updateButton);
        textView_update = (TextView) vSteps.findViewById(R.id.textView_update);
        textView_total = (TextView) vSteps.findViewById(R.id.textView_totalsteps);
        addButton.setOnClickListener(new View.OnClickListener() {
            //including onClick() method
            public void onClick(View v) {
                InsertDatabase addDatabase = new InsertDatabase();
                GetTotalSteps getTotalSteps = new GetTotalSteps();
                addDatabase.execute();
                getTotalSteps.execute();
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            //including onClick() method
            public void onClick(View v) {
                DeleteDatabase deleteDatabase = new DeleteDatabase();
                deleteDatabase.execute();
            }
        });
        readButton.setOnClickListener(new View.OnClickListener() {
            //including onClick() method
            public void onClick(View v) {
                ReadDatabase readDatabase = new ReadDatabase();
                readDatabase.execute();
                GetTotalSteps getTotalSteps = new GetTotalSteps();
                getTotalSteps.execute();
            }
        });
        updateButton.setOnClickListener(new View.OnClickListener() {
            //including onClick() method
            public void onClick(View v) {
                UpdateDatabase updateDatabase = new UpdateDatabase();
                GetTotalSteps getTotalSteps = new GetTotalSteps();
                updateDatabase.execute();
                getTotalSteps.execute();
            }
        });
        return vSteps;
    }

    /**
     * insert steps to the database
     */
    private class InsertDatabase extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {

            String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());

            if (!(editText.getText().toString().isEmpty())) {
                String[] details = editText.getText().toString().split(" ");
                if (details.length == 1 ) {
                    Steps steps = new Steps(currentDateTimeString, details[0]);
                    long id = db.stepsDao().insert(steps);
                    return (id + " " + currentDateTimeString + " " + details[0]);
                } else
                    return "";
            } else
                return "";
        }

        @Override
        protected void onPostExecute(String details) {
            textView_insert.setText("Added Record: " + details);
        }
    }

    /**
     * read the data in the database
     */
    private class ReadDatabase extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            List<Steps> stepsEntity = db.stepsDao().getAll();
            if (!(stepsEntity.isEmpty() || stepsEntity == null)) {
                String allSteps = "";
                for (Steps temp : stepsEntity) {
                    String steps = (temp.getId() + " " + temp.getDate() + " "
                            + temp.getDailySteps());
                    allSteps = allSteps + steps;
                    allSteps = allSteps + "\n";
                }
                return allSteps;
            } else
                return "";
        }

        @Override
        protected void onPostExecute(String details) {
            textView_read.setText(details);
        }
    }

    /**
     * delete the data in the database
     */
    private class DeleteDatabase extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            db.stepsDao().deleteAll();
            return null;
        }

        protected void onPostExecute(Void param) {
            textView_delete.setText("All data was deleted");
        }
    }

    /**
     * update existed data in the database
     */
    private class UpdateDatabase extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            Steps stepEntity = null;
            String[] details = editText.getText().toString().split(" ");
            if (details.length == 2) {
                int id = Integer.parseInt(details[0]);
                stepEntity = db.stepsDao().findByID(id);
                stepEntity.setDailySteps(details[1]);

            }
            if (stepEntity != null) {
                db.stepsDao().updateSteps(stepEntity);
                return (details[0] + " " + stepEntity.getDate() + details[1]);
            }
            return "";
        }

        @Override
        protected void onPostExecute(String details) {
            textView_update.setText("Updated details: " + details);
        }
    }

    /**
     * get total steps entered by the user for this day
     */
    private class GetTotalSteps extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            List<String> stepsEntity = db.stepsDao().getTotalStepsToday();
            int allSteps = 0;
            for (String temp : stepsEntity) {
                int steps = Integer.valueOf(temp);
                allSteps = allSteps + steps;
            }
            totalSteps = String.valueOf(allSteps);
            return totalSteps;
        }

        @Override
        protected void onPostExecute(String details) {
            textView_total.setText("Today's steps are " + totalSteps);
        }
    }

}