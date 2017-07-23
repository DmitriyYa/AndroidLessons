package com.example.mwork;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class ViewJobActivity extends AppCompatActivity {
    TextView tvShortDesc;
    TextView tvLongDesc;
    TextView tvTime;
    TextView tvDate;
    TextView tvSpiner;

    Intent intent;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_job);

        tvShortDesc = (TextView) findViewById(R.id.tvShotDescView);
        tvLongDesc = (TextView) findViewById(R.id.tvLongDescView);
        tvTime = (TextView) findViewById(R.id.tvTimeView);
        tvDate = (TextView) findViewById(R.id.tvDateView);
        tvSpiner = (TextView) findViewById(R.id.tvSpinerView);


//--------------------------------------------------------------------------------------------------
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getApplicationContext(), EditJobActivity.class);
                intent.putExtra(DB.COLUMN_ID, id);
                intent.putExtra(DB.COLUMN_SHOT_DESC, tvShortDesc.getText());
                intent.putExtra(DB.COLUMN_LONG_DESC, tvLongDesc.getText());
                intent.putExtra(DB.COLUMN_TIME, tvTime.getText());
                intent.putExtra(DB.COLUMN_DATE, tvDate.getText());
                intent.putExtra(DB.COLUMN_STATUS, tvSpiner.getText());
                startActivity(intent);
            }
        });

        //извлекаем данные из intent и вставляем в поля Activity
        intent = getIntent();
        tvShortDesc.setText(intent.getStringExtra(DB.COLUMN_SHOT_DESC));
        tvLongDesc.setText(intent.getStringExtra(DB.COLUMN_LONG_DESC));
        tvTime.setText(intent.getStringExtra(DB.COLUMN_TIME));
        tvDate.setText(intent.getStringExtra(DB.COLUMN_DATE));
        tvSpiner.setText(intent.getStringExtra(DB.COLUMN_STATUS));

        id = intent.getStringExtra(DB.COLUMN_ID);
    }

}
