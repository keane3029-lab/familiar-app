package com.familiar.app.data.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.familiar.app.data.model.TrustLogEntry;

import java.util.List;

@Dao
public interface TrustDao {
    @Insert
    long insertTrustEntry(TrustLogEntry entry);

    @Query("SELECT * FROM trust_log ORDER BY timestamp DESC")
    List<TrustLogEntry> getAllEntries();

    @Query("SELECT SUM(pointsImpact) FROM trust_log")
    Integer getTotalTrustScore();

    @Query("SELECT COUNT(*) FROM trust_log WHERE isKeptPromise = 1")
    int getKeptPromiseCount();

    @Query("SELECT COUNT(*) FROM trust_log WHERE isKeptPromise = 0")
    int getMissedPromiseCount();
}
