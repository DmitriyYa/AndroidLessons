package com.example.p37sqliteinnerjoin;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static com.example.p37sqliteinnerjoin.MainActivity.*;

/**
 * Created by dima on 10.06.17.
 */
public class DBHelper extends SQLiteOpenHelper {
    public static final String TABLE_POSITION = "position";
    public static final String TABLE_PEOPLE = "people";

    public DBHelper(Context context) {
        super(context, "myDb", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(LOG_TAG, "--- onCreate datadase ---");
        ContentValues contentValues = new ContentValues();

        //создаем таблицу должности
        db.execSQL("create table "
                + TABLE_POSITION
                + "(id integer primary key,"
                + "posit text, "
                + "salary integer"
                + ");");

        //заполняем ее
        for (int i = 0; i < position_id.length; i++) {
            contentValues.clear();
            contentValues.put("id", position_id[i]);
            contentValues.put("posit", position_name[i]);
            contentValues.put("salary", position_salary[i]);
            db.insert(TABLE_POSITION, null, contentValues);
        }

        //создаем таблизу людей
        db.execSQL("create table "
                + TABLE_PEOPLE
                + "(id integer primary key autoincrement,"
                + "name text, "
                + "posid integer"
                + ");");

        //заполняем ее
        for (int i = 0; i < people_name.length; i++) {
            contentValues.clear();
            contentValues.put("name", people_name[i]);
            contentValues.put("posid", people_posid[i]);
            db.insert(TABLE_PEOPLE, null, contentValues);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
