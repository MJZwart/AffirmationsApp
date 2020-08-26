/*
 * Created by MJ Zwart on 8/26/20 6:58 PM.
 * Copyright (c) 2020. All rights reserved
 * Last modified 8/26/20 3:08 PM
 */

package nl.black.affirmationsapp;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

public class SettingsActivity extends AppCompatActivity {

    private final String NOTIFS_ON = "notifs_on";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Create Activity
        super.onCreate(savedInstanceState);
        setOnPreferenceChangedListener();

        setContentView(R.layout.settings_activity);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings, new SettingsFragment())
                .commit();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void setOnPreferenceChangedListener() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.registerOnSharedPreferenceChangeListener(new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                System.out.println("onSharedPreferenceChanged with key: " + key);
                MainActivity ma = new MainActivity();
                if(key.equals(NOTIFS_ON)){
                    if(sharedPreferences.getBoolean(NOTIFS_ON, true)){
                        System.out.println("Notifications on: true. Scheduling affirmation service.");
                        ma.scheduleAffirmationService(getApplicationContext());
                    } else if(!sharedPreferences.getBoolean(NOTIFS_ON, true)){
                        System.out.println("Notifications on: false. Cancelling affirmation service.");
                        ma.cancelAffirmationService(getApplicationContext());
                    }
                }
            }
        });
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
        }
    }
}