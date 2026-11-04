package com.familiar.app.ui.emergency;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.familiar.app.R;
import com.familiar.app.data.db.AppDatabase;
import com.familiar.app.data.model.ActivityLogEntry;

public class EmergencyOverrideActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency);

        Button btnTrigger = findViewById(R.id.btn_trigger_emergency);
        Button btnGrace = findViewById(R.id.btn_request_grace_15m);
        AppDatabase db = AppDatabase.getInstance(this);

        btnTrigger.setOnClickListener(v -> {
            Toast.makeText(this, "Emergency Override Activated! Calls, Maps, and School apps unlocked.", Toast.LENGTH_LONG).show();
            db.activityLogDao().insertLog(new ActivityLogEntry(
                    "EMERGENCY_OVERRIDE",
                    "Emergency override triggered by user. Protected allowlist active unconditionally.",
                    System.currentTimeMillis(),
                    true
            ));
        });

        btnGrace.setOnClickListener(v -> {
            Toast.makeText(this, "15-minute grace request sent for parent single-tap approval.", Toast.LENGTH_SHORT).show();
            db.activityLogDao().insertLog(new ActivityLogEntry(
                    "GRACE_REQUEST",
                    "15-minute grace extension requested before screen limit application.",
                    System.currentTimeMillis(),
                    true
            ));
        });
    }
}
