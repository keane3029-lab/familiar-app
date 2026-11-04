package com.familiar.app.data.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.familiar.app.data.model.CheckIn;

import java.util.List;

@Dao
public interface CheckInDao {
    @Insert
    long insertCheckIn(CheckIn checkIn);

    @Update
    void updateCheckIn(CheckIn checkIn);

    @Query("SELECT * FROM check_ins ORDER BY createdAt DESC")
    List<CheckIn> getAllCheckIns();

    @Query("SELECT * FROM check_ins WHERE status = 'PENDING_DISCUSSION'")
    List<CheckIn> getPendingCheckIns();
}
