package com.familiar.app.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.familiar.app.R;
import com.familiar.app.data.db.AppDatabase;
import com.familiar.app.data.model.ActivityLogEntry;
import com.familiar.app.data.model.TrustLevel;
import com.familiar.app.engine.TrustManager;
import com.familiar.app.ui.adapter.ActivityLogAdapter;
import com.familiar.app.ui.emergency.EmergencyOverrideActivity;

import java.util.List;

public class DashboardFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        TextView tvTrustLevelTitle = view.findViewById(R.id.tv_trust_level_title);
        TextView tvTrustScoreSubtitle = view.findViewById(R.id.tv_trust_score_subtitle);
        TextView tvUnlockedFreedom = view.findViewById(R.id.tv_unlocked_freedom);
        Button btnOpenEmergency = view.findViewById(R.id.btn_open_emergency);
        Button btnRequestGraceQuick = view.findViewById(R.id.btn_request_grace_quick);
        RecyclerView rvActivityLogs = view.findViewById(R.id.rv_activity_logs);

        AppDatabase db = AppDatabase.getInstance(requireContext());
        Integer totalScoreObj = db.trustDao().getTotalTrustScore();
        int totalScore = (totalScoreObj != null) ? totalScoreObj : 85;
        int keptPromises = db.trustDao().getKeptPromiseCount();

        TrustManager trustManager = new TrustManager();
        TrustLevel currentLevel = trustManager.calculateTrustLevel(totalScore);

        tvTrustLevelTitle.setText(currentLevel.getTitle());
        tvTrustScoreSubtitle.setText("Trust Score: " + totalScore + " pts • " + keptPromises + " Kept Promises");
        tvUnlockedFreedom.setText("Current Freedom: " + currentLevel.getFreedomsGranted());

        btnOpenEmergency.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), EmergencyOverrideActivity.class);
            startActivity(intent);
        });

        btnRequestGraceQuick.setOnClickListener(v -> {
            Toast.makeText(requireContext(), "Grace extension request sent to parent (15m)", Toast.LENGTH_SHORT).show();
            db.activityLogDao().insertLog(new ActivityLogEntry(
                    "GRACE_REQUEST",
                    "Teen requested 15-minute grace extension. Parent approved with single tap.",
                    System.currentTimeMillis(),
                    true
            ));
            refreshLogs(db, rvActivityLogs);
        });

        rvActivityLogs.setLayoutManager(new LinearLayoutManager(requireContext()));
        refreshLogs(db, rvActivityLogs);

        return view;
    }

    private void refreshLogs(AppDatabase db, RecyclerView rv) {
        List<ActivityLogEntry> logs = db.activityLogDao().getAllLogs();
        if (logs.isEmpty()) {
            db.activityLogDao().insertLog(new ActivityLogEntry(
                    "SYSTEM_INIT",
                    "Familiar safety engine active. Protected emergency allowlist loaded.",
                    System.currentTimeMillis(),
                    true
            ));
            logs = db.activityLogDao().getAllLogs();
        }
        rv.setAdapter(new ActivityLogAdapter(logs));
    }
}
