package com.familiar.app.worker;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.familiar.app.data.db.AppDatabase;
import com.familiar.app.data.model.ActivityLogEntry;
import com.familiar.app.data.model.Rule;
import com.familiar.app.engine.RuleEngine;

import java.util.List;

public class ScheduledRuleCheckWorker extends Worker {

    public ScheduledRuleCheckWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        AppDatabase db = AppDatabase.getInstance(getApplicationContext());
        List<Rule> agreedRules = db.ruleDao().getAgreedRules();

        RuleEngine engine = new RuleEngine();
        long now = System.currentTimeMillis();

        for (Rule rule : agreedRules) {
            // Check offline rule state and log periodic status
            ActivityLogEntry log = engine.createPlainLanguageLog("SCHEDULED_CHECK", rule.getTitle(), true);
            db.activityLogDao().insertLog(log);
        }

        return Result.success();
    }
}
