package com.example.p24livecycletwoactivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class Main2Activity extends AppCompatActivity {

    final String TAG = "States";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Log.d(TAG, "TwoActivity: onCreate()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "TwoActivity: onRestart()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "TwoActivity: onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "TwoActivity: onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "TwoActivity: onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "TwoActivity: onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "TwoActivity: onDestroy()");
    }
}
