package com.familiar.app.data.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.familiar.app.data.model.AppAccessRequest;

import java.util.List;

@Dao
public interface AppAccessRequestDao {
    @Insert
    long insertRequest(AppAccessRequest request);

    @Update
    void updateRequest(AppAccessRequest request);

    @Query("SELECT * FROM app_access_requests ORDER BY requestedAt DESC")
    List<AppAccessRequest> getAllRequests();

    @Query("SELECT * FROM app_access_requests WHERE status = 'PENDING'")
    List<AppAccessRequest> getPendingRequests();
}
