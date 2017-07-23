package com.example.p23oneactivitystate;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    /**
     https://developer.android.com/guide/components/activities.html#Lifecycle

     Resumed - Activity видно на экране, оно находится в фокусе, пользователь может с ним взаимодействовать.
     Это состояние также иногда называют Running.

     Paused - Activity не в фокусе, пользователь не может с ним взаимодействовать,
     но его видно (оно перекрыто другим Activity, которое занимает не весь экран или полупрозрачно).

     Stopped - Activity не видно (полностью перекрывается другим Activity),
     соответственно оно не в фокусе и пользователь не может с ним взаимодействовать.

     Когда Activity переходит из одного состояния в другое,
     система вызывает различные его методы, которые мы можем заполнять своим кодом

     onCreate() – вызывается при первом создании Activity
     onStart() – вызывается перед тем, как Activity будет видно пользователю
     onResume() – вызывается перед тем как будет доступно для активности пользователя (взаимодействие)

     onPause() – вызывается перед тем, как будет показано другое Activity
     onStop() – вызывается когда Activity становится не видно пользователю
     onDestroy() – вызывается перед тем, как Activity будет уничтожено

     Примечание. При реализации этих методов жизненного цикла всегда вызывайте реализацию суперкласса,
     прежде чем выполнять какие-либо действия.

     * @param savedInstanceState
     */

    final String TAG="States";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG,"MainActivity: onCreate()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG,"MainActivity: onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG,"MainActivity: onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG,"MainActivity: onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG,"MainActivity: onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"MainActivity: onDestroy()");
    }
}
