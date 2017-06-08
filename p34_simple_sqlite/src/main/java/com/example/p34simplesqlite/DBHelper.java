package com.example.p34simplesqlite;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static com.example.p34simplesqlite.MainActivity.LOG_TAG;

/**
 * Created by dima on 07.06.17.
 *
 * Этот класс предоставит нам методы для создания или обновления БД в случаях ее отсутствия или устаревания.
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

//    onCreate - метод, который будет вызван, если БД, к которой мы хотим подключиться – не существует
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(LOG_TAG, "--- onCreate database ---");
        // создаем таблицу с полями
        db.execSQL("create table mytable ("
                + "id integer primary key autoincrement,"
                + "name text,"
                + "email text" + ");");
    }

//    onUpgrade - будет вызван в случае, если мы пытаемся подключиться к БД более новой версии, чем существующая
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
