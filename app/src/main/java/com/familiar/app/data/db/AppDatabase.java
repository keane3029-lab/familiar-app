package com.familiar.app.data.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.familiar.app.data.model.ActivityLogEntry;
import com.familiar.app.data.model.AppAccessRequest;
import com.familiar.app.data.model.CheckIn;
import com.familiar.app.data.model.CoachingGoal;
import com.familiar.app.data.model.FamilyAgreement;
import com.familiar.app.data.model.Rule;
import com.familiar.app.data.model.TrustLogEntry;

@Database(entities = {
        Rule.class,
        TrustLogEntry.class,
        CheckIn.class,
        AppAccessRequest.class,
        CoachingGoal.class,
        FamilyAgreement.class,
        ActivityLogEntry.class
}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static volatile AppDatabase INSTANCE;

    public abstract RuleDao ruleDao();
    public abstract TrustDao trustDao();
    public abstract CheckInDao checkInDao();
    public abstract AppAccessRequestDao appAccessRequestDao();
    public abstract CoachingDao coachingDao();
    public abstract FamilyMeetingDao familyMeetingDao();
    public abstract ActivityLogDao activityLogDao();

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "familiar_db")
                            .allowMainThreadQueries() // Local-first helper for UI state
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
