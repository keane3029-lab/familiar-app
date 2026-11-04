package com.familiar.app.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "trust_log")
public class TrustLogEntry {
    @PrimaryKey(autoGenerate = true)
    private long id;

    private String loggedBy; // "PARENT" or "TEEN"
    private String title;
    private String description;
    private boolean isKeptPromise;
    private int pointsImpact;
    private long timestamp;
    private String milestoneTarget;

    public TrustLogEntry(String loggedBy, String title, String description, boolean isKeptPromise,
                         int pointsImpact, long timestamp, String milestoneTarget) {
        this.loggedBy = loggedBy;
        this.title = title;
        this.description = description;
        this.isKeptPromise = isKeptPromise;
        this.pointsImpact = pointsImpact;
        this.timestamp = timestamp;
        this.milestoneTarget = milestoneTarget;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public String getLoggedBy() { return loggedBy; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public boolean isKeptPromise() { return isKeptPromise; }
    public int getPointsImpact() { return pointsImpact; }
    public long getTimestamp() { return timestamp; }
    public String getMilestoneTarget() { return milestoneTarget; }
}
