package com.familiar.app;

import android.app.Application;

import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.familiar.app.data.db.AppDatabase;
import com.familiar.app.data.model.Rule;

import java.util.concurrent.TimeUnit;

public class FamiliarApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Seed initial default agreed rules if database is clean
        AppDatabase db = AppDatabase.getInstance(this);
        if (db.ruleDao().getAllRules().isEmpty()) {
            long now = System.currentTimeMillis();
            db.ruleDao().insertRule(new Rule(
                    "Bedtime Wind-Down",
                    "Soft bedtime limit proposed and agreed mutually.",
                    "PARENT",
                    "PARENT_SET",
                    "AGREED",
                    "Social Media",
                    120,
                    1260, // 21:00
                    420,  // 07:00
                    false,
                    now
            ));
            db.ruleDao().insertRule(new Rule(
                    "Homework First Choice",
                    "Teen decided study app priority before gaming.",
                    "TEEN",
                    "TEEN_DECIDED",
                    "AGREED",
                    "Gaming Apps",
                    60,
                    0,
                    0,
                    false,
                    now
            ));
        }

        // Schedule periodic background rule check with WorkManager
        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.NOT_REQUIRED) // Offline safe
                .build();

        PeriodicWorkRequest checkRequest = new PeriodicWorkRequest.Builder(
                com.familiar.app.worker.ScheduledRuleCheckWorker.class,
                15, TimeUnit.MINUTES)
                .setConstraints(constraints)
                .build();

        WorkManager.getInstance(this).enqueue(checkRequest);
    }
}
