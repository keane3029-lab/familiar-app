package com.familiar.app.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.familiar.app.R;
import com.familiar.app.ui.checkin.CheckInFragment;
import com.familiar.app.ui.coaching.CoachingFragment;
import com.familiar.app.ui.dashboard.DashboardFragment;
import com.familiar.app.ui.privacy.TransparencyFragment;
import com.familiar.app.ui.rules.RuleManagerFragment;
import com.familiar.app.ui.trust.TrustFragment;
import com.familiar.app.util.PermissionHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (android.os.Build.VERSION.SDK_INT >= 33) {
            requestPermissions(new String[]{"android.permission.POST_NOTIFICATIONS"}, 1001);
        }

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            int itemId = item.getItemId();

            if (itemId == R.id.nav_dashboard) {
                selectedFragment = new DashboardFragment();
            } else if (itemId == R.id.nav_rules) {
                selectedFragment = new RuleManagerFragment();
            } else if (itemId == R.id.nav_transparency) {
                selectedFragment = new TransparencyFragment();
            } else if (itemId == R.id.nav_trust) {
                selectedFragment = new TrustFragment();
            } else if (itemId == R.id.nav_coaching) {
                selectedFragment = new CoachingFragment();
            }

            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, selectedFragment)
                        .commit();
            }
            return true;
        });

        // Default screen
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new DashboardFragment())
                    .commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (PermissionHelper.hasUsageAccess(this) == false) {
            startActivity(PermissionHelper.usageAccessIntent());
        }
    }
}
