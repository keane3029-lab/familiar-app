package com.familiar.app.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "activity_logs")
public class ActivityLogEntry {
    @PrimaryKey(autoGenerate = true)
    private long id;

    private String actionType;
    private String plainLanguageSummary;
    private long timestamp;
    private boolean isOfflineAction;

    public ActivityLogEntry(String actionType, String plainLanguageSummary, long timestamp, boolean isOfflineAction) {
        this.actionType = actionType;
        this.plainLanguageSummary = plainLanguageSummary;
        this.timestamp = timestamp;
        this.isOfflineAction = isOfflineAction;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public String getActionType() { return actionType; }
    public String getPlainLanguageSummary() { return plainLanguageSummary; }
    public long getTimestamp() { return timestamp; }
    public boolean isOfflineAction() { return isOfflineAction; }
}
