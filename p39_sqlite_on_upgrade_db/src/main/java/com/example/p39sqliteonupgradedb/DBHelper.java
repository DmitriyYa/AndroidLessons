package com.example.p39sqliteonupgradedb;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static com.example.p39sqliteonupgradedb.MainActivity.DB_NAME;
import static com.example.p39sqliteonupgradedb.MainActivity.DB_VERSION;
import static com.example.p39sqliteonupgradedb.MainActivity.LOG_TAG;

/**
 * Created by dima on 13.06.17.
 */
public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(LOG_TAG, " --- onCreate database --- ");

        //данные для таблицы людей
        String[] people_name = {"Иван", "Марья", "Петр", "Антон", "Даша", "Борис", "Костя", "Игорь"};
        int[] people_posid = {2, 3, 2, 2, 3, 1, 2, 4};

        //данные для таблицы должностей
        int[] position_id = {1, 2, 3, 4};
        String[] position_name = {"Директор", "Программер", "Бухгалтер", "Охранник"};
        int[] position_salary = {13000, 12000, 10000, 8000};

        ContentValues cv = new ContentValues();

        // создаем таблицу должностей
        db.execSQL("create table position ("
                + "id integer primary key,"
                + "name text, salary integer);");

        // заполняем ее
        for (int i = 0; i < position_id.length; i++) {
            cv.clear();
            cv.put("id", position_id[i]);
            cv.put("name", position_name[i]);
            cv.put("salary", position_salary[i]);
            db.insert("position", null, cv);
        }

//        создаем таблицу людей
        db.execSQL("create table people ("
                + "id integer primary key autoincrement,"
                + "name text, posid integer);");

        // заполняем ее
        for (int i=0;i<people_name.length;i++){
            cv.clear();
            cv.put("name",people_name[i]);
            cv.put("posid",people_posid[i]);
            db.insert("people",null,cv);
        }

    }

    //План обновления такой:
//    * - создаем и заполняем данными таблицу position
//    * - добавляем в таблицу people столбец – posid для хранения id из position
//    * - заполняем people.posid данными из position в зависимости от значения people.position
//    * - удаляем столбец people.position
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(LOG_TAG, "--- onUpgrate database from " + oldVersion + " to " + newVersion + " version ---");
        if (oldVersion == 1 && newVersion == 2) {
            ContentValues contentValues = new ContentValues();

            //данне для таблицы должностей
            int[] position_id = {1, 2, 3, 4};
            String[] position_name = {"Директор", "Программер", "Бухгалтер", "Охранник"};
            int[] position_salary = {15000, 13000, 10000, 8000};
            db.beginTransaction();
            try {

                //создаем таблицу должностей
                db.execSQL("create table position ("
                        + "id integer primary key,"
                        + "name text, salary integer);");

                //заполняем ее
                for (int i = 0; i < position_id.length; i++) {
                    contentValues.put("id", position_id[i]);
                    contentValues.put("name", position_name[i]);
                    contentValues.put("salary", position_salary[i]);
                    db.insert("position", null, contentValues);
                }

                //- добавляем в таблицу people столбец – posid для хранения id из position
                db.execSQL("alter table people add column posid integer;");

                for (int i = 0; i < position_id.length; i++) {
                    contentValues.clear();
                    contentValues.put("posid", position_id[i]);
                    db.update("people", contentValues, "position = ?", new String[]{position_name[i]});
                }

                //создаем временную таблицу
                db.execSQL("create temporary table people_tmp ("
                        + "id integer, name text, position text, posid integer);");

                //перекидываем данные из таблицы people в people_tmp
                db.execSQL("insert into people_tmp select id, name, position, posid from people;");
                db.execSQL("drop table people;");

                //создаем заново таблицу people
                db.execSQL("create table people ("
                        + "id integer primary key autoincrement,"
                        + "name text, posid integer);");

                //перекидываем данные из таблицы people_tmp в people
                db.execSQL("insert into people select id, name, posid from people_tmp;");

                //удаляем временную таблицу people_tmp
                db.execSQL("drop table people_tmp;");

                db.setTransactionSuccessful();
            } finally {
                db.endTransaction();
            }

        }
    }
}
