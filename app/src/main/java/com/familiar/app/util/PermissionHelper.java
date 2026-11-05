package com.familiar.app.util;

import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.os.Process;
import android.provider.Settings;

public final class PermissionHelper {

    private PermissionHelper() {}

    public static boolean hasUsageAccess(Context context) {
        AppOpsManager appOps = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
        int mode = appOps.checkOpNoThrow(
                AppOpsManager.OPSTR_GET_USAGE_STATS,
                Process.myUid(),
                context.getPackageName());
        return mode == AppOpsManager.MODE_ALLOWED;
    }

    public static Intent usageAccessIntent() {
        return new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
    }
}
