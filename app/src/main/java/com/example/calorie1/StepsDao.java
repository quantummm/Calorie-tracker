package com.example.calorie1;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import java.util.List;
import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * this class is part of Room and is responsible for the method
 * that access the database tables and include some operation
 *
 * @author Rongzhi Wang
 * @version 1.1
 */
@Dao
public interface StepsDao {
    @Query("SELECT * FROM steps")
    List<Steps> getAll();
    @Query("SELECT * FROM steps WHERE uid = :stepsId LIMIT 1")
    Steps findByID(int stepsId);
    @Query("SELECT dailySteps FROM steps")
    List<String> getTotalStepsToday();
    @Insert
    void insertAll(Steps... stepss);
    @Insert
    long insert(Steps steps);
    @Delete
    void delete(Steps steps);
    @Update(onConflict = REPLACE)
    public void updateSteps(Steps... stepss);
    @Query("DELETE FROM steps")
    void deleteAll();
}

