package com.familiar.app.service;

import java.util.Arrays;
import java.util.List;

// Problem 6: Accidental phone lockouts safeguard
public class EmergencyOverrideManager {

    // Protected allowlist for school apps, calls, maps, and emergency contacts that can NEVER be locked
    private static final List<String> PROTECTED_ALLOWLIST = Arrays.asList(
            "com.android.dialer",
            "com.android.contacts",
            "com.google.android.apps.maps",
            "com.google.android.apps.classroom",
            "com.instructure.candroid", // Canvas Student
            "com.familiar.app"          // App itself
    );

    public boolean isAppProtected(String packageName) {
        if (packageName == null) return true;
        for (String allowed : PROTECTED_ALLOWLIST) {
            if (packageName.toLowerCase().contains(allowed.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    public List<String> getProtectedAllowlist() {
        return PROTECTED_ALLOWLIST;
    }

    // Check 10-minute and 2-minute pre-limit warnings
    public String checkPreLimitWarning(int remainingMinutes) {
        if (remainingMinutes == 10) {
            return "WARNING: 10 minutes remaining before screen limit. You can request a 15m grace extension.";
        } else if (remainingMinutes == 2) {
            return "WARNING: 2 minutes remaining! Talk or request grace now.";
        }
        return null;
    }
}
