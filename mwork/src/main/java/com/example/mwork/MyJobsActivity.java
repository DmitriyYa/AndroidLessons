package com.example.mwork;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MyJobsActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnCreate;
    Button btnEdit;
    Button btnDelete;

    DBHelper dbHelper;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_jobs);


        btnCreate = (Button) findViewById(R.id.btnCreateJob);
        btnCreate.setOnClickListener(this);

        btnEdit = (Button) findViewById(R.id.btnEditJob);
        btnEdit.setOnClickListener(this);

        btnDelete = (Button) findViewById(R.id.btnDeleteJob);
        btnDelete.setOnClickListener(this);

        createList();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCreateJob:
                intent = new Intent(this, CreateJobActivity.class);
                startActivityForResult(intent, 1);
                break;
            case R.id.btnEditJob:
                // TODO: 19.06.17 реализовать кнопку редакт
                break;
            case R.id.btnDeleteJob:
                // TODO: 19.06.17 реализовать кнопку удалить
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        if (resultCode == RESULT_OK) {
            Toast.makeText(this, "Задача создана", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Задача не создана", Toast.LENGTH_SHORT).show();
        }

    }

    public void createList(){
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.llMyJobs);
        LayoutInflater layoutInflater = getLayoutInflater();

        //создаем объект для создания и управления версиями БД
        dbHelper = new DBHelper(this);

        //подключаемся к базе
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        //получаем выборку с данными
        String selection = "executor = ?";
        String[] selectionArgs = new String[]{"я"};
        Cursor cursor = db.query("job", null, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            int prioritet = cursor.getColumnIndex("prioritet");
            int shortDescription = cursor.getColumnIndex("shortDescription");
            int date = cursor.getColumnIndex("date");

            do {
                View item = layoutInflater.inflate(R.layout.one_task_in_list_myjobs, linearLayout, false);

                TextView tvshortDescription = (TextView) item.findViewById(R.id.tvShortDecription);
                tvshortDescription.setText(cursor.getString(shortDescription));

                TextView tvPrioritet = (TextView) item.findViewById(R.id.tvPrioriti);
                tvPrioritet.setText(cursor.getString(prioritet));

                TextView tvDate = (TextView) item.findViewById(R.id.tvDate);
                tvDate.setText(cursor.getString(date));

                item.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
                item.setBackgroundColor(Color.parseColor("#b2dfdb"));
                linearLayout.addView(item);
            }

            //С помощью метода moveToNext мы перебираем все строки в Cursor пока не добираемся до последней.
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
    }
}
