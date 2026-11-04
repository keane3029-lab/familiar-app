package com.familiar.app;

import com.familiar.app.data.model.TrustLevel;
import com.familiar.app.engine.TrustManager;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TrustManagerTest {

    private TrustManager manager;

    @Before
    public void setUp() {
        manager = new TrustManager();
    }

    @Test
    public void testTrustLevelCalculation_Level1() {
        TrustLevel level = manager.calculateTrustLevel(20);
        assertEquals(TrustLevel.LEVEL_1, level);
    }

    @Test
    public void testTrustLevelCalculation_Level2() {
        TrustLevel level = manager.calculateTrustLevel(65);
        assertEquals(TrustLevel.LEVEL_2, level);
    }

    @Test
    public void testTrustLevelCalculation_Level3() {
        TrustLevel level = manager.calculateTrustLevel(110);
        assertEquals(TrustLevel.LEVEL_3, level);
    }

    @Test
    public void testTrustLevelCalculation_Level4() {
        TrustLevel level = manager.calculateTrustLevel(160);
        assertEquals(TrustLevel.LEVEL_4, level);
    }

    @Test
    public void testPromisePointsCalculation() {
        assertEquals(10, manager.calculatePointsForPromise(true));
        assertEquals(-5, manager.calculatePointsForPromise(false));
    }

    @Test
    public void testFreedomGrants() {
        String freedoms = manager.getUnlockedFreedoms(TrustLevel.LEVEL_3);
        assertTrue(freedoms.contains("Auto small purchase approvals"));
    }
}
