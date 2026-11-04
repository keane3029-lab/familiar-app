package com.familiar.app.data.model;

public enum TrustLevel {
    LEVEL_1("Level 1: Starter Trust", 0, "Basic allowed apps, 8:00 PM Bedtime, Manual purchase approvals"),
    LEVEL_2("Level 2: Growing Independence", 50, "Social apps allowed (up to 1.5h), 9:00 PM Bedtime flexibility"),
    LEVEL_3("Level 3: Responsible Choice", 100, "Custom app choices, 10:00 PM Bedtime, Auto small purchase approvals ($10 limit)"),
    LEVEL_4("Level 4: Autonomous Managed", 150, "Full app choices, flexible bedtime, self-managed requests");

    private final String title;
    private final int requiredScore;
    private final String freedomsGranted;

    TrustLevel(String title, int requiredScore, String freedomsGranted) {
        this.title = title;
        this.requiredScore = requiredScore;
        this.freedomsGranted = freedomsGranted;
    }

    public String getTitle() { return title; }
    public int getRequiredScore() { return requiredScore; }
    public String getFreedomsGranted() { return freedomsGranted; }
}
