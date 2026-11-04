package com.familiar.app.engine;

import com.familiar.app.data.model.TrustLevel;

public class TrustManager {

    // Problem 3: Tiered trust levels (Level 1 to Level 4)
    public TrustLevel calculateTrustLevel(int totalScore) {
        if (totalScore >= TrustLevel.LEVEL_4.getRequiredScore()) {
            return TrustLevel.LEVEL_4;
        } else if (totalScore >= TrustLevel.LEVEL_3.getRequiredScore()) {
            return TrustLevel.LEVEL_3;
        } else if (totalScore >= TrustLevel.LEVEL_2.getRequiredScore()) {
            return TrustLevel.LEVEL_2;
        } else {
            return TrustLevel.LEVEL_1;
        }
    }

    public String getUnlockedFreedoms(TrustLevel level) {
        return level.getFreedomsGranted();
    }

    public int getNextMilestoneTarget(TrustLevel currentLevel) {
        switch (currentLevel) {
            case LEVEL_1:
                return TrustLevel.LEVEL_2.getRequiredScore();
            case LEVEL_2:
                return TrustLevel.LEVEL_3.getRequiredScore();
            case LEVEL_3:
                return TrustLevel.LEVEL_4.getRequiredScore();
            case LEVEL_4:
                return TrustLevel.LEVEL_4.getRequiredScore();
            default:
                return 50;
        }
    }

    // Problem 4: Trust changes only through agreed milestones, not through silent judgment
    public int calculatePointsForPromise(boolean isKeptPromise) {
        return isKeptPromise ? 10 : -5;
    }
}
