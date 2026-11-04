package com.familiar.app.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.familiar.app.R;
import com.familiar.app.data.model.AppAccessRequest;

import java.util.List;

public class AppRequestAdapter extends RecyclerView.Adapter<AppRequestAdapter.AppRequestViewHolder> {

    private final List<AppAccessRequest> requests;

    public AppRequestAdapter(List<AppAccessRequest> requests) {
        this.requests = requests;
    }

    @NonNull
    @Override
    public AppRequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_app_request, parent, false);
        return new AppRequestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppRequestViewHolder holder, int position) {
        AppAccessRequest req = requests.get(position);
        holder.tvAppName.setText(req.getAppName() + " (" + req.getStatus() + ")");
        holder.tvReason.setText("Reason: " + req.getReasonText());
        String summary = "Usage Summary: " + req.getDailyAverageUsageMinutes() + " mins daily average • Category: " + req.getCategory();
        holder.tvSummary.setText(summary);
    }

    @Override
    public int getItemCount() { return requests.size(); }

    static class AppRequestViewHolder extends RecyclerView.ViewHolder {
        TextView tvAppName, tvReason, tvSummary;

        public AppRequestViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAppName = itemView.findViewById(R.id.tv_request_app_name);
            tvReason = itemView.findViewById(R.id.tv_request_reason);
            tvSummary = itemView.findViewById(R.id.tv_request_summary);
        }
    }
}
