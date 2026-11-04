package com.familiar.app.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "coaching_goals")
public class CoachingGoal {
    @PrimaryKey(autoGenerate = true)
    private long id;

    private long weekStartDate;
    private int teenGoalMinutes;
    private String reflectionText;
    private boolean parentSummaryViewable;
    private String suggestedSolution;

    public CoachingGoal(long weekStartDate, int teenGoalMinutes, String reflectionText,
                        boolean parentSummaryViewable, String suggestedSolution) {
        this.weekStartDate = weekStartDate;
        this.teenGoalMinutes = teenGoalMinutes;
        this.reflectionText = reflectionText;
        this.parentSummaryViewable = parentSummaryViewable;
        this.suggestedSolution = suggestedSolution;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public long getWeekStartDate() { return weekStartDate; }
    public int getTeenGoalMinutes() { return teenGoalMinutes; }
    public String getReflectionText() { return reflectionText; }
    public boolean isParentSummaryViewable() { return parentSummaryViewable; }
    public String getSuggestedSolution() { return suggestedSolution; }
}
