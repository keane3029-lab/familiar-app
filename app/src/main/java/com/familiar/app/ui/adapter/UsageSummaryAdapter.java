package com.familiar.app.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.familiar.app.R;
import com.familiar.app.data.model.UsageSummary;

import java.util.List;

public class UsageSummaryAdapter extends RecyclerView.Adapter<UsageSummaryAdapter.UsageViewHolder> {

    private final List<UsageSummary> summaries;

    public UsageSummaryAdapter(List<UsageSummary> summaries) {
        this.summaries = summaries;
    }

    @NonNull
    @Override
    public UsageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_usage_summary, parent, false);
        return new UsageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UsageViewHolder holder, int position) {
        UsageSummary summary = summaries.get(position);
        holder.tvCategory.setText(summary.getCategoryName());
        holder.tvTime.setText(summary.getFormattedTime());
    }

    @Override
    public int getItemCount() { return summaries.size(); }

    static class UsageViewHolder extends RecyclerView.ViewHolder {
        TextView tvCategory, tvTime;

        public UsageViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCategory = itemView.findViewById(R.id.tv_usage_category);
            tvTime = itemView.findViewById(R.id.tv_usage_time);
        }
    }
}
