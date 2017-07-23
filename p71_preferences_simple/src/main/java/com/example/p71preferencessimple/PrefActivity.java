package com.example.p71preferencessimple;

import android.preference.PreferenceActivity;
import android.os.Bundle;

public class PrefActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref);
    }
}
