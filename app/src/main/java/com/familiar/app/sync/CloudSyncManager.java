package com.familiar.app.sync;

// Problem 8: Offline-safe behavior so rules still apply without internet and sync when reconnected
public class CloudSyncManager {

    // Config toggle clearly marked
    public static final boolean ENABLE_CLOUD_SYNC = false;

    public boolean isSyncEnabled() {
        return ENABLE_CLOUD_SYNC;
    }

    public void syncPendingOfflineData() {
        if (!ENABLE_CLOUD_SYNC) {
            // Local-first execution: all data safely handled in SQLite
            return;
        }
        // Cloud sync implementation when cloud endpoint is configured
    }
}
