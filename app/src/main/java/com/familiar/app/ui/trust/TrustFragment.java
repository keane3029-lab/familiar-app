package com.familiar.app.ui.trust;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.familiar.app.R;
import com.familiar.app.data.db.AppDatabase;
import com.familiar.app.data.model.TrustLevel;
import com.familiar.app.data.model.TrustLogEntry;
import com.familiar.app.engine.TrustManager;
import com.familiar.app.ui.adapter.TrustLogAdapter;

import java.util.List;

public class TrustFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trust, container, false);

        TextView tvHeader = view.findViewById(R.id.tv_trust_tier_header);
        ProgressBar pbProgress = view.findViewById(R.id.pb_trust_progress);
        TextView tvMilestoneDesc = view.findViewById(R.id.tv_trust_milestone_desc);
        EditText etPromiseTitle = view.findViewById(R.id.et_promise_title);
        RadioButton rbKept = view.findViewById(R.id.rb_kept);
        Button btnAddEntry = view.findViewById(R.id.btn_add_trust_entry);
        RecyclerView rvTrustLog = view.findViewById(R.id.rv_trust_log);

        AppDatabase db = AppDatabase.getInstance(requireContext());
        TrustManager trustManager = new TrustManager();

        rvTrustLog.setLayoutManager(new LinearLayoutManager(requireContext()));
        updateTrustState(db, trustManager, tvHeader, pbProgress, tvMilestoneDesc, rvTrustLog);

        btnAddEntry.setOnClickListener(v -> {
            String title = etPromiseTitle.getText().toString().trim();
            if (title.isEmpty()) {
                Toast.makeText(requireContext(), "Please enter promise details", Toast.LENGTH_SHORT).show();
                return;
            }
            boolean isKept = rbKept.isChecked();
            int points = trustManager.calculatePointsForPromise(isKept);

            TrustLogEntry entry = new TrustLogEntry(
                    "TEEN",
                    title,
                    isKept ? "Kept agreed commitment" : "Missed agreed commitment",
                    isKept,
                    points,
                    System.currentTimeMillis(),
                    "Agreed Milestone Goal"
            );

            db.trustDao().insertTrustEntry(entry);
            etPromiseTitle.setText("");
            Toast.makeText(requireContext(), "Promise logged to shared history", Toast.LENGTH_SHORT).show();
            updateTrustState(db, trustManager, tvHeader, pbProgress, tvMilestoneDesc, rvTrustLog);
        });

        return view;
    }

    private void updateTrustState(AppDatabase db, TrustManager manager, TextView tvHeader,
                                 ProgressBar pb, TextView tvDesc, RecyclerView rv) {
        Integer scoreObj = db.trustDao().getTotalTrustScore();
        int score = (scoreObj != null) ? scoreObj : 85;
        TrustLevel level = manager.calculateTrustLevel(score);

        tvHeader.setText("Current Freedom Tier: " + level.getTitle());
        pb.setProgress(Math.min(100, score));
        tvDesc.setText("Next Milestone Target: " + manager.getNextMilestoneTarget(level) +
                " pts • Unlocked Freedom: " + manager.getUnlockedFreedoms(level));

        List<TrustLogEntry> entries = db.trustDao().getAllEntries();
        if (entries.isEmpty()) {
            db.trustDao().insertTrustEntry(new TrustLogEntry("PARENT", "Initial Agreed Setup", "Established base trust baseline", true, 85, System.currentTimeMillis(), "Baseline"));
            entries = db.trustDao().getAllEntries();
        }
        rv.setAdapter(new TrustLogAdapter(entries));
    }
}
