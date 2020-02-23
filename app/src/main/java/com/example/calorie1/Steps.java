package com.example.calorie1;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * this class is the entity class and will be used to create Steps table locally
 *
 * @author Rongzhi Wang
 * @version 1.1
 */
@Entity
public class Steps {
        @PrimaryKey(autoGenerate = true)
        public int uid;
        @ColumnInfo(name = "date")
        public String date;
        @ColumnInfo(name = "dailySteps")
        public String dailySteps;

        public Steps(String date, String dailySteps) {
            this.date=date;
            this.dailySteps=dailySteps;
        }

        public int getId() {
            return uid;
        }

        public String getDate() {
            return date;
        }
        public void setDate(String firstName) {
            this.date = firstName;
        }
        public String getDailySteps() {
            return dailySteps;
        }
        public void setDailySteps(String lastName) {
            this.dailySteps = lastName;
        }
    }

