package com.familiar.app.data.model;

public class UsageSummary {
    private final String categoryName;
    private final int totalMinutesToday;

    public UsageSummary(String categoryName, int totalMinutesToday) {
        this.categoryName = categoryName;
        this.totalMinutesToday = totalMinutesToday;
    }

    public String getCategoryName() { return categoryName; }
    public int getTotalMinutesToday() { return totalMinutesToday; }

    public String getFormattedTime() {
        int hours = totalMinutesToday / 60;
        int mins = totalMinutesToday % 60;
        if (hours > 0) {
            return hours + "h " + mins + "m today";
        }
        return mins + "m today";
    }
}
