package com.familiar.app.ui.checkin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.familiar.app.R;
import com.familiar.app.data.db.AppDatabase;
import com.familiar.app.data.model.AppAccessRequest;
import com.familiar.app.data.model.CheckIn;
import com.familiar.app.ui.adapter.AppRequestAdapter;
import com.familiar.app.ui.adapter.CheckInAdapter;

import java.util.List;

public class CheckInFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_checkin, container, false);

        EditText etAppName = view.findViewById(R.id.et_app_request_name);
        EditText etReason = view.findViewById(R.id.et_app_request_reason);
        Button btnSubmitRequest = view.findViewById(R.id.btn_submit_app_request);
        RecyclerView rvCheckins = view.findViewById(R.id.rv_checkins);
        RecyclerView rvAppRequests = view.findViewById(R.id.rv_app_requests);

        AppDatabase db = AppDatabase.getInstance(requireContext());

        rvCheckins.setLayoutManager(new LinearLayoutManager(requireContext()));
        rvAppRequests.setLayoutManager(new LinearLayoutManager(requireContext()));

        refreshLists(db, rvCheckins, rvAppRequests);

        btnSubmitRequest.setOnClickListener(v -> {
            String appName = etAppName.getText().toString().trim();
            String reason = etReason.getText().toString().trim();
            if (appName.isEmpty() || reason.isEmpty()) {
                Toast.makeText(requireContext(), "Please enter app name and reason", Toast.LENGTH_SHORT).show();
                return;
            }

            long now = System.currentTimeMillis();
            long expiresAt = now + (24 * 60 * 60 * 1000L); // Problem 7: 24h decision window

            AppAccessRequest request = new AppAccessRequest(
                    appName,
                    "com.app." + appName.toLowerCase().replaceAll("\s+", ""),
                    reason,
                    now,
                    expiresAt,
                    "PENDING",
                    "Educational & Productivity",
                    25 // Problem 7: Usage summary shown to parent
            );

            db.appAccessRequestDao().insertRequest(request);
            Toast.makeText(requireContext(), "Access request submitted with 24h window", Toast.LENGTH_SHORT).show();
            etAppName.setText("");
            etReason.setText("");
            refreshLists(db, rvCheckins, rvAppRequests);
        });

        return view;
    }

    private void refreshLists(AppDatabase db, RecyclerView rvCheckins, RecyclerView rvRequests) {
        List<CheckIn> checkIns = db.checkInDao().getAllCheckIns();
        if (checkIns.isEmpty()) {
            db.checkInDao().insertCheckIn(new CheckIn(
                    1,
                    "Bedtime Social Media Limit",
                    "Need 15 extra minutes to finish school group project communication.",
                    "PENDING_DISCUSSION",
                    System.currentTimeMillis(),
                    0
            ));
            checkIns = db.checkInDao().getAllCheckIns();
        }
        rvCheckins.setAdapter(new CheckInAdapter(checkIns));

        List<AppAccessRequest> requests = db.appAccessRequestDao().getAllRequests();
        if (requests.isEmpty()) {
            db.appAccessRequestDao().insertRequest(new AppAccessRequest(
                    "Photo Editor Studio",
                    "com.studio.photoeditor",
                    "Needed for digital art course assignments.",
                    System.currentTimeMillis(),
                    System.currentTimeMillis() + (24 * 60 * 60 * 1000L),
                    "PENDING",
                    "Creativity",
                    20
            ));
            requests = db.appAccessRequestDao().getAllRequests();
        }
        rvRequests.setAdapter(new AppRequestAdapter(requests));
    }
}
