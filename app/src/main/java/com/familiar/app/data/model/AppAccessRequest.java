package com.familiar.app.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "app_access_requests")
public class AppAccessRequest {
    @PrimaryKey(autoGenerate = true)
    private long id;

    private String appName;
    private String packageName;
    private String reasonText;
    private long requestedAt;
    private long expiresAt; // 24h default decision window
    private String status; // "PENDING", "APPROVED", "DENIED"
    private String category;
    private int dailyAverageUsageMinutes;

    public AppAccessRequest(String appName, String packageName, String reasonText, long requestedAt,
                            long expiresAt, String status, String category, int dailyAverageUsageMinutes) {
        this.appName = appName;
        this.packageName = packageName;
        this.reasonText = reasonText;
        this.requestedAt = requestedAt;
        this.expiresAt = expiresAt;
        this.status = status;
        this.category = category;
        this.dailyAverageUsageMinutes = dailyAverageUsageMinutes;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public String getAppName() { return appName; }
    public String getPackageName() { return packageName; }
    public String getReasonText() { return reasonText; }
    public long getRequestedAt() { return requestedAt; }
    public long getExpiresAt() { return expiresAt; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getCategory() { return category; }
    public int getDailyAverageUsageMinutes() { return dailyAverageUsageMinutes; }
}
