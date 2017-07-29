package com.example.p73preferencesenable;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

//В этом уроке:
//- управляем активностью настроек (setEnabled)

//Иногда в настройках можно наблюдать, как некоторые из них неактивны - с ними нельзя взаимодействовать.
//Причем это зависит от смежных настроек.
//Т.е. выключили одну галку - стала  неактивна другая.
//Разберемся, как это делается.

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem mi = menu.add(0, 1, 0, "Preferences");
        mi.setIntent(new Intent(this, PrefActivity.class));
        return super.onCreateOptionsMenu(menu);
    }
}
