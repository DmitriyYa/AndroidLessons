package com.example.p72preferencessimple2;

//В этом уроке:
//- используем в настройках список
//- группируем настройки по экранам и категориям

//Создадим простое приложение.
//На первом экране будем читать и отображать значение из настройки-списка.
//Второй экран будет отображать все настройки.

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView tvInfo;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvInfo = (TextView) findViewById(R.id.tvInfo);

        //В onCreate получаем доступ к настройкам.
        sp = PreferenceManager.getDefaultSharedPreferences(this);

    }

    @Override
    protected void onResume() {
        //В onResume выводим в tvInfo значение из настроек, которое записал туда список.
        //Если значений нет, то пишем текст «не выбрано»
        String listValue = sp.getString("list", "не выбрано");
        tvInfo.setText("Значение списка - " + listValue);
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //В onCreateOptionsMenu создаем пункт меню и вешаем на него Intent, который запустит нам экран настроек.
        MenuItem mi = menu.add(0, 1, 0, "Preferences");
        mi.setIntent(new Intent(this, PrefActivity.class));
        return super.onCreateOptionsMenu(menu);
    }
}
