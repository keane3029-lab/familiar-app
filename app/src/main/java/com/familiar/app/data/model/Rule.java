package com.familiar.app.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "rules")
public class Rule {
    @PrimaryKey(autoGenerate = true)
    private long id;

    private String title;
    private String description;
    private String proposedBy; // "PARENT" or "TEEN"
    private String ruleCategory; // "PARENT_SET" or "TEEN_DECIDED"
    private String status; // "PROPOSED", "AGREED", "ACTIVE", "REJECTED"
    private String targetApp;
    private int dailyLimitMinutes;
    private int bedtimeStartMinutes; // e.g., 21:00 -> 1260
    private int bedtimeEndMinutes;   // e.g., 07:00 -> 420
    private boolean isEmergencyProtected;
    private long createdAt;

    public Rule(String title, String description, String proposedBy, String ruleCategory,
                String status, String targetApp, int dailyLimitMinutes,
                int bedtimeStartMinutes, int bedtimeEndMinutes, boolean isEmergencyProtected, long createdAt) {
        this.title = title;
        this.description = description;
        this.proposedBy = proposedBy;
        this.ruleCategory = ruleCategory;
        this.status = status;
        this.targetApp = targetApp;
        this.dailyLimitMinutes = dailyLimitMinutes;
        this.bedtimeStartMinutes = bedtimeStartMinutes;
        this.bedtimeEndMinutes = bedtimeEndMinutes;
        this.isEmergencyProtected = isEmergencyProtected;
        this.createdAt = createdAt;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getProposedBy() { return proposedBy; }
    public String getRuleCategory() { return ruleCategory; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getTargetApp() { return targetApp; }
    public int getDailyLimitMinutes() { return dailyLimitMinutes; }
    public int getBedtimeStartMinutes() { return bedtimeStartMinutes; }
    public int getBedtimeEndMinutes() { return bedtimeEndMinutes; }
    public boolean isEmergencyProtected() { return isEmergencyProtected; }
    public long getCreatedAt() { return createdAt; }
}
