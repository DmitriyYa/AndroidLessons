package com.example.mwork;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnMyJobs;
    Button btnControlJobs;
    Button btnAnalisis;
    Button btnCalc;

    TextView tvMyJobsCount;
    TextView tvControlJobsCount;

    Intent intent;

    DBHelper dbHelper;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // создаем объект для создания и управления версиями БД
        dbHelper = new DBHelper(this);

        btnMyJobs = (Button) findViewById(R.id.btnMyJobs);
        btnMyJobs.setOnClickListener(this);

        btnControlJobs = (Button) findViewById(R.id.btnControlJobs);
        btnControlJobs.setOnClickListener(this);

        btnAnalisis = (Button) findViewById(R.id.btnAnalisis);
        btnAnalisis.setOnClickListener(this);

        btnCalc = (Button) findViewById(R.id.btnCalc);
        btnCalc.setOnClickListener(this);

        tvMyJobsCount = (TextView) findViewById(R.id.tvMyJobsCount);
        tvMyJobsCount.setText(getMyJobsCount());

        tvControlJobsCount = (TextView) findViewById(R.id.tvControlJobsCount);
        tvControlJobsCount.setText(getControlJobsCount());

    }

    @Override
    protected void onStart() {
        super.onStart();
        tvMyJobsCount.setText(getMyJobsCount());
        tvControlJobsCount.setText(getControlJobsCount());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnMyJobs:
                intent = new Intent(this, MyJobsActivity.class);
                startActivity(intent);
                break;

            case R.id.btnControlJobs:
                intent = new Intent(this, ControlJobsActivity.class);
                startActivity(intent);
                break;

            case R.id.btnAnalisis:
                intent = new Intent(this, AnalisisActivity.class);
                startActivity(intent);
                break;

//            case R.id.btnCalc:
// TODO: 16.06.17 вызвать приложение Калькулятор
        }
    }

    public String getMyJobsCount() {
        String result;
        String selection = "executor = ?";
        String[] selectionArgs = new String[]{"я"};
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("job", null, selection, selectionArgs, null, null, null);
        result = Integer.toString(cursor.getCount());
        cursor.close();
        db.close();
        return result;
    }

    public String getControlJobsCount() {
        String result;
        String selection = "executor = ?";
        String[] selectionArgs = new String[]{"не я"};
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("job", null, selection, selectionArgs, null, null, null);
        result = Integer.toString(cursor.getCount());
        cursor.close();
        db.close();
        return result;
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (data == null) {
//            return;
//        }
//        if (resultCode == RESULT_OK) {
//            Toast.makeText(this, "Задача создана", Toast.LENGTH_SHORT).show();
//        }
//        else {
//            Toast.makeText(this, "Задача не создана", Toast.LENGTH_SHORT).show();
//        }
//
//    }
}
