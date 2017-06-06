package com.example.p26intentfilter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ShowDateOther extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_date_other);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, MMM d, yyyy");
        String date=simpleDateFormat.format(new Date(System.currentTimeMillis()));

        TextView textView= (TextView) findViewById(R.id.textV);
        textView.setText(date);
    }
}
