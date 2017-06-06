package com.example.p27getintentaction;

import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
//http://startandroid.ru/ru/uroki/vse-uroki-spiskom/65-urok-27-chitaem-action-iz-intent.html

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonTime= (Button) findViewById(R.id.btnTime);
        Button buttonDate= (Button) findViewById(R.id.btnDate);

        buttonDate.setOnClickListener(this);
        buttonTime.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent intent;

        switch (v.getId()){
            case R.id.btnTime:
                intent=new Intent("androidlessons.intent.action.showtime");
                startActivity(intent);
                break;
            case R.id.btnDate:
                intent=new Intent("androidlessons.intent.action.showdate");
                startActivity(intent);
                break;
        }
    }
}
