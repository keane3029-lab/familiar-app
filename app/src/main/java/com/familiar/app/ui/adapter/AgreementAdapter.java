package com.familiar.app.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.familiar.app.R;
import com.familiar.app.data.model.FamilyAgreement;

import java.util.List;

public class AgreementAdapter extends RecyclerView.Adapter<AgreementAdapter.AgreementViewHolder> {

    private final List<FamilyAgreement> agreements;

    public AgreementAdapter(List<FamilyAgreement> agreements) {
        this.agreements = agreements;
    }

    @NonNull
    @Override
    public AgreementViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_agreement, parent, false);
        return new AgreementViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AgreementViewHolder holder, int position) {
        FamilyAgreement agreement = agreements.get(position);
        holder.tvTitle.setText(agreement.getTitle());
        holder.tvCompromise.setText("Agreed Compromise: " + agreement.getProposedCompromise());
        holder.tvCooldown.setText("48-Hour Cooldown Protection Active (Changes require written reason)");
    }

    @Override
    public int getItemCount() { return agreements.size(); }

    static class AgreementViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvCompromise, tvCooldown;

        public AgreementViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_agreement_title);
            tvCompromise = itemView.findViewById(R.id.tv_agreement_compromise);
            tvCooldown = itemView.findViewById(R.id.tv_agreement_cooldown);
        }
    }
}
