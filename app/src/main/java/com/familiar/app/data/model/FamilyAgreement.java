package com.familiar.app.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "family_agreements")
public class FamilyAgreement {
    @PrimaryKey(autoGenerate = true)
    private long id;

    private String title;
    private String teenWants;
    private String parentWants;
    private String teenWorries;
    private String parentWorries;
    private String proposedCompromise;
    private long agreedAt;
    private long cooldownUntilTimestamp; // 48h cooldown before changes take effect
    private String changeReason;

    public FamilyAgreement(String title, String teenWants, String parentWants, String teenWorries,
                           String parentWorries, String proposedCompromise, long agreedAt,
                           long cooldownUntilTimestamp, String changeReason) {
        this.title = title;
        this.teenWants = teenWants;
        this.parentWants = parentWants;
        this.teenWorries = teenWorries;
        this.parentWorries = parentWorries;
        this.proposedCompromise = proposedCompromise;
        this.agreedAt = agreedAt;
        this.cooldownUntilTimestamp = cooldownUntilTimestamp;
        this.changeReason = changeReason;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public String getTitle() { return title; }
    public String getTeenWants() { return teenWants; }
    public String getParentWants() { return parentWants; }
    public String getTeenWorries() { return teenWorries; }
    public String getParentWorries() { return parentWorries; }
    public String getProposedCompromise() { return proposedCompromise; }
    public long getAgreedAt() { return agreedAt; }
    public long getCooldownUntilTimestamp() { return cooldownUntilTimestamp; }
    public String getChangeReason() { return changeReason; }
}
