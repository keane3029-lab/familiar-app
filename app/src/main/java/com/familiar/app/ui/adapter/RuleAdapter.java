package com.familiar.app.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.familiar.app.R;
import com.familiar.app.data.model.Rule;

import java.util.List;

public class RuleAdapter extends RecyclerView.Adapter<RuleAdapter.RuleViewHolder> {

    private final List<Rule> rules;

    public RuleAdapter(List<Rule> rules) {
        this.rules = rules;
    }

    @NonNull
    @Override
    public RuleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rule, parent, false);
        return new RuleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RuleViewHolder holder, int position) {
        Rule rule = rules.get(position);
        holder.tvTitle.setText(rule.getTitle());
        holder.tvStatus.setText(rule.getStatus());
        String meta = "Category: " + rule.getTargetApp() + " • Limit: " + rule.getDailyLimitMinutes() +
                "m daily • Proposed by " + rule.getProposedBy() + " (" + rule.getRuleCategory() + ")";
        holder.tvDesc.setText(meta);
    }

    @Override
    public int getItemCount() { return rules.size(); }

    static class RuleViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvStatus, tvDesc;

        public RuleViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_item_rule_title);
            tvStatus = itemView.findViewById(R.id.tv_item_rule_status);
            tvDesc = itemView.findViewById(R.id.tv_item_rule_desc);
        }
    }
}
