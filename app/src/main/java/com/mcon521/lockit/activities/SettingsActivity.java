package com.mcon521.lockit.activities;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreference;

import com.mcon521.lockit.R;
import com.mcon521.lockit.lib.Utils;

public class SettingsActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.settings, new SettingsFragment())
                    .commit();
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
            setLoginPage();
        }

        private void setLoginPage() {
            SwitchPreference requireLoginPreference = findPreference(getString(R.string.requireLogin));
            if (requireLoginPreference != null) {
                requireLoginPreference.setOnPreferenceChangeListener((preference, newValue) -> {
                    Boolean newBooleanValue = (Boolean) newValue;
                    Utils.setRequireLogin(newBooleanValue);
                    return true;
                });
            }
        }
    }
}