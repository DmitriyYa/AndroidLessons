package com.example.mwork;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by dima on 17.06.17.
 */

public class DBHelper extends SQLiteOpenHelper {


    public DBHelper(Context context) {
        // конструктор суперкласса
        //context - контекст
        //mydb - название базы данных
        //null – объект для работы с курсорами, нам пока не нужен, поэтому null
        //1 – версия базы данных
        super(context, "myDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // создаем таблицу с полями
        db.execSQL("create table job (" +
                "id integer primary key autoincrement, " +
                "executor text, " +
                "shortDescription text, " +
                "longDescription text, " +
                "prioritet text, " +
                "time text, " +
                "date text);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
