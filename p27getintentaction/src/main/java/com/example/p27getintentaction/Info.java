package com.example.p27getintentaction;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Info extends AppCompatActivity {

//Мы получаем Intent с помощью метода getIntent(),
// читаем из него action и в зависимости от значения формируем и выводим на экран текст.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        // получаем Intent, который вызывал это Activity
        Intent intent=getIntent();

        // читаем из него action
        String action=intent.getAction();

        String format="";
        String textInfo="";

        // в зависимости от action заполняем переменные
        if (action.equals("androidlessons.intent.action.showtime")){
            format="HH:mm:ss";
            textInfo="Time: ";
        }
        else if (action.equals("androidlessons.intent.action.showdate")){
            format="dd.MM.yyyy";
            textInfo="Date: ";
        }

        // в зависимости от содержимого переменной format
        // получаем дату или время в переменную datetime
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat(format);
        String dateTime=simpleDateFormat.format(new Date(System.currentTimeMillis()));

        TextView textView= (TextView) findViewById(R.id.tvInfo);
        textView.setText(textInfo+dateTime);
    }

//    Не забываем прописать новое Activity в манифесте
// и создать ему Intent Filter с двумя action и category. И label укажите Date/time info.
}
