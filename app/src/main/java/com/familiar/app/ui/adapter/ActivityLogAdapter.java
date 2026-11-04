package com.familiar.app.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.familiar.app.R;
import com.familiar.app.data.model.ActivityLogEntry;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ActivityLogAdapter extends RecyclerView.Adapter<ActivityLogAdapter.LogViewHolder> {

    private final List<ActivityLogEntry> logs;
    private final SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a", Locale.getDefault());

    public ActivityLogAdapter(List<ActivityLogEntry> logs) {
        this.logs = logs;
    }

    @NonNull
    @Override
    public LogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_activity_log, parent, false);
        return new LogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LogViewHolder holder, int position) {
        ActivityLogEntry entry = logs.get(position);
        holder.tvTime.setText(timeFormat.format(new Date(entry.getTimestamp())));
        holder.tvSummary.setText(entry.getPlainLanguageSummary());
    }

    @Override
    public int getItemCount() { return logs.size(); }

    static class LogViewHolder extends RecyclerView.ViewHolder {
        TextView tvTime, tvSummary;

        public LogViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTime = itemView.findViewById(R.id.tv_log_time);
            tvSummary = itemView.findViewById(R.id.tv_log_summary);
        }
    }
}
