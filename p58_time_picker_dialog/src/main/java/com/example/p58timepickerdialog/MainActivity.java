package com.example.p58timepickerdialog;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
//http://startandroid.ru/ru/uroki/vse-uroki-spiskom/117-urok-58-dialogi-timepickerdialog.html

//В этом уроке:
//- используем TimePickerDialog


    int DIALOG_TIME = 1;
    int myHour = 14;
    int myMinute = 35;
    TextView tvTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTime = (TextView) findViewById(R.id.tvTime);
        tvTime.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        //В onClick вызываем метод showDialog и передаем ему ID диалога.
        // Этот метод (showDialog) создает диалог с помощью отдельного метода и показывает его.
        // ID используется для указания, какой именно диалог создавать и показывать.

        showDialog(DIALOG_TIME);
    }

    //Метод onCreateDialog – это и есть отдельный метод, который вызывается в showDialog для создания диалога.
    // В этом методе мы смотрим, какой ID пришел на вход, создаем соответствующий диалог и возвращаем его.
    @Override
    protected Dialog onCreateDialog(int id) {

        if (id == DIALOG_TIME) {

            //context – контекст
            //callBack – это обработчик с интерфйесом TimePickerDialog.OnTimeSetListener,
            //           метод которого срабатывает при нажатии кнопки ОК на диалоге
            //hourOfDay – час, который покажет диалог
            //minute – минута, которую покажет диалог
            //is24HourView – формат времени 24 часа (иначе AM/PM)
            TimePickerDialog tpd = new TimePickerDialog(this, myCallBack, myHour, myMinute, true);
            return tpd;
        }
        return super.onCreateDialog(id);
    }

    TimePickerDialog.OnTimeSetListener myCallBack = new TimePickerDialog.OnTimeSetListener() {

        //myCallBack – объект, реализующий интерфейс TimePickerDialog.OnTimeSetListener.
        // У него только один метод – onTimeSet, который предоставляет нам TimePicker из диалога,
        // и час и минуту, которые он показывает. Т.е. то, что мы ввели в диалоге.
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            myHour = hourOfDay;
            myMinute = minute;
            tvTime.setText("Time is " + myHour + " hours " + myMinute + " minutes");
        }
    };


}
