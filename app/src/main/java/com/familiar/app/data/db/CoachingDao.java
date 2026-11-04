package com.familiar.app.data.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.familiar.app.data.model.CoachingGoal;

import java.util.List;

@Dao
public interface CoachingDao {
    @Insert
    long insertGoal(CoachingGoal goal);

    @Update
    void updateGoal(CoachingGoal goal);

    @Query("SELECT * FROM coaching_goals ORDER BY weekStartDate DESC LIMIT 1")
    CoachingGoal getLatestGoal();

    @Query("SELECT * FROM coaching_goals ORDER BY weekStartDate DESC")
    List<CoachingGoal> getAllGoals();
}
