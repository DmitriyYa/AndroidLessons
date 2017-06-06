package com.example.p24livecycletwoactivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

/**
 * http://startandroid.ru/ru/uroki/vse-uroki-spiskom/61-urok-24-activity-lifecycle-primer-smeny-sostojanij-s-dvumja-activity.html
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    final String TAG = "States";
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button= (Button) findViewById(R.id.button);
        button.setOnClickListener(this);

        Log.d(TAG, "MainActivity: onCreate()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "MainActivity: onRestart()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "MainActivity: onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "MainActivity: onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "MainActivity: onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "MainActivity: onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "MainActivity: onDestroy()");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button:
                Intent intent=new Intent(this,Main2Activity.class);
                startActivity(intent);
        }
    }
}
