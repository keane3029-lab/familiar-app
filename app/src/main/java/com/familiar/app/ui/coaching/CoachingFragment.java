package com.familiar.app.ui.coaching;

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
import com.familiar.app.data.model.CoachingGoal;
import com.familiar.app.data.model.FamilyAgreement;
import com.familiar.app.ui.adapter.AgreementAdapter;

import java.util.List;

public class CoachingFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_coaching, container, false);

        EditText etGoalMins = view.findViewById(R.id.et_coaching_goal_mins);
        EditText etReflection = view.findViewById(R.id.et_coaching_reflection);
        Button btnSaveGoal = view.findViewById(R.id.btn_save_coaching_goal);

        EditText etMeetingTitle = view.findViewById(R.id.et_meeting_title);
        EditText etWants = view.findViewById(R.id.et_wants);
        EditText etWorries = view.findViewById(R.id.et_worries);
        EditText etCompromise = view.findViewById(R.id.et_compromise);
        Button btnSaveAgreement = view.findViewById(R.id.btn_save_family_agreement);

        RecyclerView rvAgreements = view.findViewById(R.id.rv_agreements);
        AppDatabase db = AppDatabase.getInstance(requireContext());

        rvAgreements.setLayoutManager(new LinearLayoutManager(requireContext()));
        refreshAgreements(db, rvAgreements);

        btnSaveGoal.setOnClickListener(v -> {
            int mins = etGoalMins.getText().toString().isEmpty() ? 120 : Integer.parseInt(etGoalMins.getText().toString());
            String reflection = etReflection.getText().toString().trim();

            CoachingGoal goal = new CoachingGoal(
                    System.currentTimeMillis(),
                    mins,
                    reflection.isEmpty() ? "Reflected on balanced study and recreation." : reflection,
                    true,
                    "Teen proposed studying before gaming sessions."
            );

            db.coachingDao().insertGoal(goal);
            Toast.makeText(requireContext(), "Weekly Coaching Goal saved! Parent sees summary only.", Toast.LENGTH_SHORT).show();
            etGoalMins.setText("");
            etReflection.setText("");
        });

        // Problem 10: Family meeting tool & 48-hour cooldown
        btnSaveAgreement.setOnClickListener(v -> {
            String title = etMeetingTitle.getText().toString().trim();
            String wants = etWants.getText().toString().trim();
            String worries = etWorries.getText().toString().trim();
            String compromise = etCompromise.getText().toString().trim();

            if (title.isEmpty() || compromise.isEmpty()) {
                Toast.makeText(requireContext(), "Please fill in meeting title and proposed compromise", Toast.LENGTH_SHORT).show();
                return;
            }

            long now = System.currentTimeMillis();
            long cooldownUntil = now + (48 * 60 * 60 * 1000L); // 48-hour cooldown period

            FamilyAgreement agreement = new FamilyAgreement(
                    title,
                    wants.isEmpty() ? "Teen wants extra weekend flexibility" : wants,
                    "Parent wants school work completed first",
                    worries.isEmpty() ? "Teen worries about missing group project work" : worries,
                    "Parent worries about sleep deprivation",
                    compromise,
                    now,
                    cooldownUntil,
                    "Initial calm family agreement"
            );

            db.familyMeetingDao().insertAgreement(agreement);
            Toast.makeText(requireContext(), "Family Agreement created with 48h cooldown protection!", Toast.LENGTH_SHORT).show();
            etMeetingTitle.setText("");
            etWants.setText("");
            etWorries.setText("");
            etCompromise.setText("");
            refreshAgreements(db, rvAgreements);
        });

        return view;
    }

    private void refreshAgreements(AppDatabase db, RecyclerView rv) {
        List<FamilyAgreement> agreements = db.familyMeetingDao().getAllAgreements();
        if (agreements.isEmpty()) {
            long now = System.currentTimeMillis();
            db.familyMeetingDao().insertAgreement(new FamilyAgreement(
                    "Weekend Curfew & Screen Compromise",
                    "Teen wants 10:30 PM weekend bedtime",
                    "Parent wants 10:00 PM bedtime",
                    "Teen worries about rushing evening activities",
                    "Parent worries about morning fatigue",
                    "Compromise: 10:30 PM on Friday/Saturday if homework is completed by 6 PM.",
                    now,
                    now + (48 * 60 * 60 * 1000L),
                    "Calm initial agreement"
            ));
            agreements = db.familyMeetingDao().getAllAgreements();
        }
        rv.setAdapter(new AgreementAdapter(agreements));
    }
}
