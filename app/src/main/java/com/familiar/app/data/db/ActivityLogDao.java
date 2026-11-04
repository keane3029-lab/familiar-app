package com.familiar.app.data.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.familiar.app.data.model.ActivityLogEntry;

import java.util.List;

@Dao
public interface ActivityLogDao {
    @Insert
    long insertLog(ActivityLogEntry log);

    @Query("SELECT * FROM activity_logs ORDER BY timestamp DESC LIMIT 50")
    List<ActivityLogEntry> getAllLogs();
}
