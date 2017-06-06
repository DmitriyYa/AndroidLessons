package com.example.p26intentfilter;

import java.text.SimpleDateFormat;
import java.util.Date;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ShowDate extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd:MM:yyyy");
        String date=simpleDateFormat.format(new Date(System.currentTimeMillis()));

        TextView textView= (TextView) findViewById(R.id.textV);
        textView.setText(date);
    }
}
