package com.example.p69parcelable;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class SecondActivity extends AppCompatActivity {
    final String LOG_TAG = "myLogs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second);

        Log.d(LOG_TAG, "getParcelableExtra");

        //Мы вытаскиваем наш MyObject-объект из Intent и в лог выводим значения s и i.
        MyObject myObject = (MyObject) getIntent().getParcelableExtra(MyObject.class.getCanonicalName());
        Log.d(LOG_TAG, "myObj: " + myObject.s + ", " + myObject.i);
    }
}
