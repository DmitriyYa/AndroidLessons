package com.example.p71preferencessimple;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

//В этом уроке:
//- используем Preferences для работы с настройками приложения

public class MainActivity extends AppCompatActivity {

    TextView tvInfo;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvInfo = (TextView) findViewById(R.id.tvInfo);

        // получаем SharedPreferences, которое работает с файлом настроек
        sp = PreferenceManager.getDefaultSharedPreferences(this);

        // полная очистка настроек
//        sp.edit().clear().commit();
    }

    //читаем из SharedPreferences настройки и выводим их в TextView.
    //При чтении используем те самые ключи, которые прописывали в xml-файле в атрибутах key.
    @Override
    protected void onResume() {
        Boolean notif = sp.getBoolean("notif", false);
        String addres = sp.getString("address", "");
        String text = "Notification are "
                + ((notif) ? "enable, addres = " + addres : "disable");
        tvInfo.setText(text);
        super.onResume();
    }

    //просто настраиваем меню для вызова окна настроек.
    //Мы создаем пункт меню и вешаем на него Intent - в итоге при нажатии вызовется Activity.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem mi = menu.add(0, 1, 0, "Preferences");
        mi.setIntent(new Intent(this, PrefActivity.class));
        return super.onCreateOptionsMenu(menu);
    }
}
