package com.familiar.app;

import com.familiar.app.data.model.Rule;
import com.familiar.app.engine.RuleEngine;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RuleEngineTest {

    private RuleEngine engine;
    private List<Rule> existingRules;

    @Before
    public void setUp() {
        engine = new RuleEngine();
        existingRules = new ArrayList<>();
        existingRules.add(new Rule(
                "Bedtime Social",
                "Bedtime rule",
                "PARENT",
                "PARENT_SET",
                "AGREED",
                "Social Media",
                120,
                1260, // 21:00
                420,  // 07:00
                false,
                System.currentTimeMillis()
        ));
    }

    @Test
    public void testRulePreviewCalculation() {
        Rule proposed = new Rule("Study Limit", "Desc", "TEEN", "TEEN_DECIDED", "PROPOSED", "Games", 60, 0, 0, false, System.currentTimeMillis());
        RuleEngine.RulePreviewResult preview = engine.previewRule(proposed, existingRules);

        assertEquals(2, preview.getEstimatedHoursSaved());
        assertFalse(preview.hasConflicts());
    }

    @Test
    public void testRuleConflictDetection() {
        Rule conflicting = new Rule("Duplicate Bedtime", "Desc", "PARENT", "PARENT_SET", "PROPOSED", "Social Media", 60, 1260, 420, false, System.currentTimeMillis());
        List<String> conflicts = engine.detectConflicts(conflicting, existingRules);

        assertFalse(conflicts.isEmpty());
        assertTrue(conflicts.get(0).contains("Conflict detected"));
    }

    @Test
    public void testAppRestrictionWhenLimitExceeded() {
        Rule rule = existingRules.get(0);
        boolean restricted = engine.isAppRestricted(rule, 720, 130); // 130 mins > 120 limit
        assertTrue(restricted);
    }

    @Test
    public void testEmergencyProtectionPreventsRestriction() {
        Rule protectedRule = new Rule("Emergency Contact", "Desc", "PARENT", "PARENT_SET", "AGREED", "com.android.dialer", 0, 0, 0, true, System.currentTimeMillis());
        boolean restricted = engine.isAppRestricted(protectedRule, 1300, 500);
        assertFalse(restricted);
    }
}
