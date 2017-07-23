package com.example.mwork;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by dima on 22.06.17.
 */

public class DB {

    private static final String DB_NAME = "dbMWork";
    private static final int DB_VERSION = 1;
    private static final String DB_TABLE = "tasks";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_SHOT_DESC = "shortDescription";
    public static final String COLUMN_LONG_DESC = "longDescription";
    public static final String COLUMN_TIME = "myTime";
    public static final String COLUMN_DATE = "myDate";
    public static final String COLUMN_STATUS = "status";

    private static final String DB_CREATE =
            "create table " + DB_TABLE + "(" +
                    COLUMN_ID + " integer primary key autoincrement, " +
                    COLUMN_SHOT_DESC + " text, " +
                    COLUMN_LONG_DESC + " text, " +
                    COLUMN_TIME + " text, " +
                    COLUMN_DATE + " text, " +
                    COLUMN_STATUS + " text " +
                    ");";

    private final Context mCtx;

    private DBHelper mDBHelper;
    private SQLiteDatabase mDB;

    public DB(Context ctx) {
        mCtx = ctx;
    }

    // открыть подключение
    public SQLiteDatabase open() {
        mDBHelper = new DBHelper(mCtx, DB_NAME, null, DB_VERSION);
        mDB = mDBHelper.getWritableDatabase();
        return mDB;
    }

    // закрыть подключение
    public void close() {
        if (mDBHelper != null) mDBHelper.close();
    }

    // получить все данные из таблицы DB_TABLE
    public Cursor getAllData() {
        return mDB.query(DB_TABLE, null, null, null, null, null, null);
    }

    // добавить запись в DB_TABLE
    public void addRec(String shotDesc, String longDesc, String time, String date, String status) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_SHOT_DESC, shotDesc);
        cv.put(COLUMN_LONG_DESC, longDesc);
        cv.put(COLUMN_TIME, time);
        cv.put(COLUMN_DATE, date);
        cv.put(COLUMN_STATUS, status);
        mDB.insert(DB_TABLE, null, cv);
    }

    // удалить запись из DB_TABLE
    public void delRec(long id) {
        mDB.delete(DB_TABLE, COLUMN_ID + " = " + id, null);
    }

    // вернуть запись из DB_TABLE по id
    public Cursor getRec(long id) {
        String selection="_id = ?";
        String[] selectionArgs=new String[]{String.valueOf(id)};
        return mDB.query(DB_TABLE, null, selection,selectionArgs,null,null,null);
    }

    //обновить запись в таблице по id
    public void updRec(String id, String shotDesc, String longDesc, String myTime, String myDate, String status ){

        ContentValues contentValues=new ContentValues();
        contentValues.put(DB.COLUMN_SHOT_DESC, shotDesc);
        contentValues.put(DB.COLUMN_LONG_DESC, longDesc);
        contentValues.put(DB.COLUMN_TIME, myTime);
        contentValues.put(DB.COLUMN_DATE, myDate);
        contentValues.put(DB.COLUMN_STATUS, status);

        mDB.update(DB_TABLE,contentValues,"_id = ?", new String[]{id});
    }


    // класс по созданию и управлению БД
    private class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        // создаем БД
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DB_CREATE);

//            ContentValues cv = new ContentValues();
//            for (int i = 1; i < 15; i++) {
//                cv.put(COLUMN_SHOT_DESC, "shot des " + i);
//                cv.put(COLUMN_LONG_DESC, "long des" + i);
//                cv.put(COLUMN_TIME, "00:00");
//                cv.put(COLUMN_DATE, "26 июня 2017");
//                cv.put(COLUMN_STATUS, "не приступал");
//                db.insert(DB_TABLE, null, cv);
//            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
    }
}
