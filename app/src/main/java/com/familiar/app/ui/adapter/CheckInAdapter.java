package com.familiar.app.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.familiar.app.R;
import com.familiar.app.data.model.CheckIn;

import java.util.List;

public class CheckInAdapter extends RecyclerView.Adapter<CheckInAdapter.CheckInViewHolder> {

    private final List<CheckIn> checkIns;

    public CheckInAdapter(List<CheckIn> checkIns) {
        this.checkIns = checkIns;
    }

    @NonNull
    @Override
    public CheckInViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_checkin, parent, false);
        return new CheckInViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CheckInViewHolder holder, int position) {
        CheckIn item = checkIns.get(position);
        holder.tvTitle.setText("Check-In: " + item.getRestrictionTitle());
        holder.tvDispute.setText("Teen message: "" + item.getDisputeMessage() + """);
    }

    @Override
    public int getItemCount() { return checkIns.size(); }

    static class CheckInViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvDispute;

        public CheckInViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_checkin_title);
            tvDispute = itemView.findViewById(R.id.tv_checkin_dispute);
        }
    }
}
