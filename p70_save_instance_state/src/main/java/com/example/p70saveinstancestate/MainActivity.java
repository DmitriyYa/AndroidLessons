package com.example.p70saveinstancestate;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

//В этом уроке:
//- сохраняем данные при повороте экрана

//Когда работа Activity приостанавливается(onPause или onStop), она остается в памяти и хранит все свои объекты и их значения.
// И при возврате в Activity, все остается, как было.
// Но если приостановленное Activity уничтожается, например, при нехватке памяти,
// то соответственно удаляются и все его объекты.
// И если к нему снова вернуться, то системе надо заново его создавать и восстанавливать данные,
// которые были утеряны при уничтожении.
// Для этих целей Activity предоставляет нам для реализации пару методов:
// первый позволяет сохранить данные – onSaveInstanceState,
// а второй – восстановить - onRestoreInstanceState.

public class MainActivity extends AppCompatActivity {
    final String LOG_TAG = "myLogs";
    int cnt = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(LOG_TAG, "onCreate");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG, "onDestroy");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(LOG_TAG,"onPouse");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(LOG_TAG,"onRestart");
    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        //Из savedInstanceState вытаскиваем значение и помещаем в переменную cnt.
        //Теперь при уничтожении и воссоздании Activity переменная cnt сохранит свое значение, и наш счетчик продолжит работать.
        cnt=savedInstanceState.getInt("count");
        Log.d(LOG_TAG, "onRestoreInstanceState");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(LOG_TAG,"onResume");
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        //В объект outState мы пишем значение переменной cnt.
        //Механизм аналогичен помещению данных в Intent.
        outState.putInt("count",cnt);
        Log.d(LOG_TAG, "onSaveInstanceState");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(LOG_TAG,"onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(LOG_TAG,"onStop");
    }

    public void onclick(){
        //увеличиваем счетчик на единицу и выводим значение в тосте
        Toast.makeText(this, "Count = " + ++cnt, Toast.LENGTH_SHORT).show();
    }
}
