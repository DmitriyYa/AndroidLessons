package com.example.p26intentfilter;

import java.text.SimpleDateFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Date;

public class ShowTime extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_time);

        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("HH:mm:ss");
        String time=simpleDateFormat.format(new Date(System.currentTimeMillis()));

        TextView textView= (TextView) findViewById(R.id.textView);
        textView.setText(time);
    }
}
