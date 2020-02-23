package com.example.calorie1;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

/**
 * this class lists the entities contained in the database
 * and the DAOs which access them
 *
 * @author Rongzhi Wang
 * @version 1.1
 */
@Database(entities = {Steps.class}, version = 2, exportSchema = false)
    public abstract class StepsDatabase extends RoomDatabase {
        public abstract StepsDao stepsDao();
        private static volatile StepsDatabase INSTANCE;
        static StepsDatabase getDatabase(final Context context) {
            if (INSTANCE == null) {
                synchronized (StepsDatabase.class) {
                    if (INSTANCE == null) {
                        INSTANCE =
                                Room.databaseBuilder(context.getApplicationContext(),
                                        StepsDatabase.class, "steps_database")
                                        .build();
                    }
                }
            }
            return INSTANCE;
        }
    }


