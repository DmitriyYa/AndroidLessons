package com.example.p38sqlitetransaction;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

//http://startandroid.ru/ru/uroki/vse-uroki-spiskom/78-urok-38-tranzaktsii-v-sqlite.html

//В этом уроке:
//- используем транзакции при работе с БД

public class MainActivity extends AppCompatActivity {

    public static final String LOG_TAG = "myLogs";

    DBHelper dbHelper;
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(LOG_TAG, "--- onCreate Activity ---");
        dbHelper = new DBHelper(this);
        myAction();
    }

    private void myAction() {
        database = dbHelper.getWritableDatabase();
        delete(database, "myTable");
            database.beginTransaction();
        //Это очень важно!
        // Т.е. если вы открыли транзакцию, выполнили какие-либо действия и не закрыли транзакцию,
        // то все операции будут считаться неуспешными и изменения не будут внесены в БД.
        // Поэтому закрытие транзакции необходимо выполнять и finally нам это гарантирует.
        try {
            insert(database, "myTable", "val1");
            insert(database, "myTable", "val2");
            insert(database, "myTable", "val3");
            database.setTransactionSuccessful();
        }
        finally {
            database.endTransaction();
        }
        read(database, "myTable");
        dbHelper.close();
    }


    private void insert(SQLiteDatabase database, String table, String value) {
        Log.d(LOG_TAG, "Insert in table " + table + " value = " + value);
        ContentValues contentValues = new ContentValues();
        contentValues.put("val", value);
        database.insert(table, null, contentValues);
    }

    private void read(SQLiteDatabase database, String table) {
        Log.d(LOG_TAG, "Read table " + table);
        Cursor cursor = database.query(table, null, null, null, null, null, null);
        if (cursor != null) {
            Log.d(LOG_TAG, "Records count = " + cursor.getCount());
            if (cursor.moveToFirst()) {
                do {
                    Log.d(LOG_TAG, cursor.getString(cursor.getColumnIndex("val")));
                }
                while (cursor.moveToNext());
            }
        }
        cursor.close();
    }

    private void delete(SQLiteDatabase database, String table) {
        Log.d(LOG_TAG, "Delete all from table - " + table);
        database.delete(table, null, null);
    }

}
