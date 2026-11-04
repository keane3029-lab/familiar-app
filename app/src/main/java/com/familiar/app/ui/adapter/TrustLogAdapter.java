package com.familiar.app.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.familiar.app.R;
import com.familiar.app.data.model.TrustLogEntry;

import java.util.List;

public class TrustLogAdapter extends RecyclerView.Adapter<TrustLogAdapter.TrustViewHolder> {

    private final List<TrustLogEntry> entries;

    public TrustLogAdapter(List<TrustLogEntry> entries) {
        this.entries = entries;
    }

    @NonNull
    @Override
    public TrustViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_trust_entry, parent, false);
        return new TrustViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrustViewHolder holder, int position) {
        TrustLogEntry entry = entries.get(position);
        holder.tvTitle.setText(entry.getTitle());
        String meta = "Logged by " + entry.getLoggedBy() + " • " + (entry.isKeptPromise() ? "Kept Promise" : "Missed Promise");
        holder.tvMeta.setText(meta);
        holder.tvPoints.setText((entry.getPointsImpact() > 0 ? "+" : "") + entry.getPointsImpact() + " pts");
    }

    @Override
    public int getItemCount() { return entries.size(); }

    static class TrustViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvMeta, tvPoints;

        public TrustViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_trust_entry_title);
            tvMeta = itemView.findViewById(R.id.tv_trust_entry_meta);
            tvPoints = itemView.findViewById(R.id.tv_trust_entry_points);
        }
    }
}
