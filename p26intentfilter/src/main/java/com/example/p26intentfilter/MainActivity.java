package com.example.p26intentfilter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
//http://startandroid.ru/ru/uroki/vse-uroki-spiskom/64-urok-26-intent-filter-praktika.html

/**
 * Итак, мы создавали и посылали Intent с action.
 * Этот Intent находил Activity с подходящим Intent Filter и отображал его.
 * Если находил несколько – давал выбор. Примеры отлично показывают механизм.
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button buttonTime;
    Button buttonDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonTime = (Button) findViewById(R.id.buttonTime);
        buttonDate = (Button) findViewById(R.id.buttonDate);

        buttonTime.setOnClickListener(this);
        buttonDate.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;

        switch (v.getId()) {
            case R.id.buttonTime:
                intent = new Intent("androidLessons.intent.action.showTime");
                startActivity(intent);
                break;
            case R.id.buttonDate:
                intent = new Intent("androidLessons.intent.action.showDate");
                startActivity(intent);
                break;
        }
    }
}
