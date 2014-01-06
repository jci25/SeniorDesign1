package com.example.seniordesign1;

import android.os.Bundle;
import android.preference.PreferenceActivity;
 
public class UserSettingActivity extends PreferenceActivity {
 
    @SuppressWarnings("deprecation")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
 
        addPreferencesFromResource(R.layout.settings);
 
    }
}