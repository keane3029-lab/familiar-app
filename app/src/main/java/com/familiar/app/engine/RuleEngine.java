package com.familiar.app.engine;

import com.familiar.app.data.model.ActivityLogEntry;
import com.familiar.app.data.model.Rule;

import java.util.ArrayList;
import java.util.List;

public class RuleEngine {

    public static class RulePreviewResult {
        private final int estimatedHoursSaved;
        private final int impactedAppCount;
        private final List<String> detectedConflicts;

        public RulePreviewResult(int estimatedHoursSaved, int impactedAppCount, List<String> detectedConflicts) {
            this.estimatedHoursSaved = estimatedHoursSaved;
            this.impactedAppCount = impactedAppCount;
            this.detectedConflicts = detectedConflicts;
        }

        public int getEstimatedHoursSaved() { return estimatedHoursSaved; }
        public int getImpactedAppCount() { return impactedAppCount; }
        public List<String> getDetectedConflicts() { return detectedConflicts; }
        public boolean hasConflicts() { return !detectedConflicts.isEmpty(); }
    }

    // Problem 8: Preview mode - shows what a rule will do before saving it
    public RulePreviewResult previewRule(Rule newRule, List<Rule> existingRules) {
        int estimatedHoursSaved = Math.max(1, (180 - newRule.getDailyLimitMinutes()) / 60);
        int impactedAppCount = (newRule.getTargetApp() != null && !newRule.getTargetApp().isEmpty()) ? 1 : 3;
        List<String> conflicts = detectConflicts(newRule, existingRules);

        return new RulePreviewResult(estimatedHoursSaved, impactedAppCount, conflicts);
    }

    // Problem 8: Conflict detection when two rules overlap
    public List<String> detectConflicts(Rule newRule, List<Rule> existingRules) {
        List<String> conflicts = new ArrayList<>();
        if (existingRules == null) return conflicts;

        for (Rule existing : existingRules) {
            if ("AGREED".equalsIgnoreCase(existing.getStatus()) || "ACTIVE".equalsIgnoreCase(existing.getStatus())) {
                // Check if target app conflicts
                if (existing.getTargetApp() != null && existing.getTargetApp().equalsIgnoreCase(newRule.getTargetApp())) {
                    if (existing.getDailyLimitMinutes() != newRule.getDailyLimitMinutes()) {
                        conflicts.add("Conflict detected: Rule '" + existing.getTitle() +
                                "' already sets a daily limit of " + existing.getDailyLimitMinutes() +
                                "m for " + newRule.getTargetApp() + ".");
                    }
                }
                // Check if bedtime windows overlap
                if (newRule.getBedtimeStartMinutes() > 0 && existing.getBedtimeStartMinutes() > 0) {
                    if (Math.abs(newRule.getBedtimeStartMinutes() - existing.getBedtimeStartMinutes()) < 60) {
                        conflicts.add("Bedtime overlap: Proposed start time (" + newRule.getBedtimeStartMinutes() +
                                "m) conflicts with existing bedtime '" + existing.getTitle() + "'.");
                    }
                }
            }
        }
        return conflicts;
    }

    // Evaluate rules for current time / app
    public boolean isAppRestricted(Rule rule, int currentMinuteOfDay, int usageTodayMinutes) {
        if (!"AGREED".equalsIgnoreCase(rule.getStatus()) && !"ACTIVE".equalsIgnoreCase(rule.getStatus())) {
            return false;
        }
        // Protected emergency apps can never be restricted
        if (rule.isEmergencyProtected()) {
            return false;
        }
        // Check daily limit
        if (rule.getDailyLimitMinutes() > 0 && usageTodayMinutes >= rule.getDailyLimitMinutes()) {
            return true;
        }
        // Check bedtime window
        if (rule.getBedtimeStartMinutes() > 0 && rule.getBedtimeEndMinutes() > 0) {
            if (rule.getBedtimeStartMinutes() > rule.getBedtimeEndMinutes()) {
                // Overnight bedtime (e.g., 21:00 to 07:00)
                if (currentMinuteOfDay >= rule.getBedtimeStartMinutes() || currentMinuteOfDay < rule.getBedtimeEndMinutes()) {
                    return true;
                }
            } else {
                if (currentMinuteOfDay >= rule.getBedtimeStartMinutes() && currentMinuteOfDay < rule.getBedtimeEndMinutes()) {
                    return true;
                }
            }
        }
        return false;
    }

    // Problem 8: Plain-language activity log explaining every automatic action
    public ActivityLogEntry createPlainLanguageLog(String actionType, String target, boolean isOffline) {
        String summary;
        long now = System.currentTimeMillis();

        switch (actionType) {
            case "BEDTIME_START":
                summary = "Bedtime rule activated for " + target + ". Emergency contacts, maps, and school apps remain accessible.";
                break;
            case "LIMIT_WARNING_10M":
                summary = "10-minute warning given for " + target + ". Grace extension request available.";
                break;
            case "LIMIT_WARNING_2M":
                summary = "2-minute warning given for " + target + ". App will pause automatically soon.";
                break;
            case "GRACE_GRANTED":
                summary = "15-minute grace extension granted for " + target + " after parent single-tap approval.";
                break;
            case "RULE_AGREED":
                summary = "New rule '" + target + "' mutually agreed and activated by Parent and Teen.";
                break;
            default:
                summary = "Automatic safety action performed for " + target + ".";
                break;
        }

        if (isOffline) {
            summary += " (Logged offline-safe)";
        }

        return new ActivityLogEntry(actionType, summary, now, isOffline);
    }
}
