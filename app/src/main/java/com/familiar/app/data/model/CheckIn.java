package com.familiar.app.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "check_ins")
public class CheckIn {
    @PrimaryKey(autoGenerate = true)
    private long id;

    private long ruleId;
    private String restrictionTitle;
    private String disputeMessage; // Explanation from Teen
    private String status; // "PENDING_DISCUSSION", "RESOLVED_AGREED", "RESOLVED_MODIFIED"
    private long createdAt;
    private long resolvedAt;

    public CheckIn(long ruleId, String restrictionTitle, String disputeMessage,
                   String status, long createdAt, long resolvedAt) {
        this.ruleId = ruleId;
        this.restrictionTitle = restrictionTitle;
        this.disputeMessage = disputeMessage;
        this.status = status;
        this.createdAt = createdAt;
        this.resolvedAt = resolvedAt;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public long getRuleId() { return ruleId; }
    public String getRestrictionTitle() { return restrictionTitle; }
    public String getDisputeMessage() { return disputeMessage; }
    public void setDisputeMessage(String disputeMessage) { this.disputeMessage = disputeMessage; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public long getCreatedAt() { return createdAt; }
    public long getResolvedAt() { return resolvedAt; }
}
