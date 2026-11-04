package com.familiar.app.ui.rules;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.familiar.app.R;
import com.familiar.app.data.db.AppDatabase;
import com.familiar.app.data.model.Rule;
import com.familiar.app.engine.RuleEngine;
import com.familiar.app.ui.adapter.RuleAdapter;

import java.util.List;

public class RuleManagerFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rules, container, false);

        EditText etTitle = view.findViewById(R.id.et_rule_title);
        EditText etApp = view.findViewById(R.id.et_rule_app);
        EditText etLimit = view.findViewById(R.id.et_rule_limit);
        RadioGroup rgCategory = view.findViewById(R.id.rg_rule_category);
        RadioButton rbParentSet = view.findViewById(R.id.rb_parent_set);
        TextView tvPreviewBox = view.findViewById(R.id.tv_rule_preview_box);
        Button btnPreview = view.findViewById(R.id.btn_preview_rule);
        Button btnSave = view.findViewById(R.id.btn_save_rule);
        RecyclerView rvRules = view.findViewById(R.id.rv_rules);

        AppDatabase db = AppDatabase.getInstance(requireContext());
        RuleEngine engine = new RuleEngine();

        rvRules.setLayoutManager(new LinearLayoutManager(requireContext()));
        refreshRules(db, rvRules);

        btnPreview.setOnClickListener(v -> {
            String title = etTitle.getText().toString().trim();
            String app = etApp.getText().toString().trim();
            int limit = etLimit.getText().toString().isEmpty() ? 120 : Integer.parseInt(etLimit.getText().toString());
            String category = rbParentSet.isChecked() ? "PARENT_SET" : "TEEN_DECIDED";

            Rule tempRule = new Rule(title, "Preview Rule", "PARENT", category, "PROPOSED", app, limit, 1260, 420, false, System.currentTimeMillis());
            RuleEngine.RulePreviewResult preview = engine.previewRule(tempRule, db.ruleDao().getAllRules());

            StringBuilder sb = new StringBuilder();
            sb.append("PREVIEW MODE:
");
            sb.append("• Estimated time saved: ").append(preview.getEstimatedHoursSaved()).append("h daily
");
            sb.append("• Apps impacted: ").append(preview.getImpactedAppCount()).append("
");
            if (preview.hasConflicts()) {
                sb.append("• WARNING: ").append(preview.getDetectedConflicts().get(0));
            } else {
                sb.append("• Conflict check: No overlapping rule conflicts.");
            }
            tvPreviewBox.setText(sb.toString());
        });

        btnSave.setOnClickListener(v -> {
            String title = etTitle.getText().toString().trim();
            String app = etApp.getText().toString().trim();
            if (title.isEmpty() || app.isEmpty()) {
                Toast.makeText(requireContext(), "Please enter rule title and target app", Toast.LENGTH_SHORT).show();
                return;
            }
            int limit = etLimit.getText().toString().isEmpty() ? 120 : Integer.parseInt(etLimit.getText().toString());
            String category = rbParentSet.isChecked() ? "PARENT_SET" : "TEEN_DECIDED";

            Rule rule = new Rule(title, "Mutually proposed rule", "PARENT", category, "PROPOSED", app, limit, 1260, 420, false, System.currentTimeMillis());
            db.ruleDao().insertRule(rule);
            Toast.makeText(requireContext(), "Rule proposed! Reviewing mutually.", Toast.LENGTH_SHORT).show();
            etTitle.setText("");
            etApp.setText("");
            etLimit.setText("");
            refreshRules(db, rvRules);
        });

        return view;
    }

    private void refreshRules(AppDatabase db, RecyclerView rv) {
        List<Rule> rules = db.ruleDao().getAllRules();
        rv.setAdapter(new RuleAdapter(rules));
    }
}
