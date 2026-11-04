package com.familiar.app.ui.privacy;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.familiar.app.R;
import com.familiar.app.data.model.UsageSummary;
import com.familiar.app.ui.adapter.UsageSummaryAdapter;

import java.util.ArrayList;
import java.util.List;

public class TransparencyFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_transparency, container, false);

        Button btnPrivacyPolicy = view.findViewById(R.id.btn_open_privacy_policy);
        RecyclerView rvUsageTotals = view.findViewById(R.id.rv_usage_totals);

        btnPrivacyPolicy.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), PrivacyPolicyActivity.class);
            startActivity(intent);
        });

        rvUsageTotals.setLayoutManager(new LinearLayoutManager(requireContext()));

        // Problem 2: Monitoring shows categories and totals only (never message content or browsing history)
        List<UsageSummary> summaries = new ArrayList<>();
        summaries.add(new UsageSummary("Social Media", 130)); // 2h 10m
        summaries.add(new UsageSummary("Educational & Class Apps", 90));  // 1h 30m
        summaries.add(new UsageSummary("Gaming & Entertainment", 45));   // 45m
        summaries.add(new UsageSummary("Navigation & Tools", 25));       // 25m

        rvUsageTotals.setAdapter(new UsageSummaryAdapter(summaries));

        return view;
    }
}
