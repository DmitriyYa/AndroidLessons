package com.example.p39sqliteonupgradedb;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

//http://startandroid.ru/ru/uroki/vse-uroki-spiskom/79-urok-39-onupgrade-obnovljaem-bd-v-sqlite.html

//В этом уроке:
//        - меняем версию и обновляем структуру БД в onUpgrade


/**
 * Первая версия БД будет содержать только таблицу people с именем сотрудника и его должностью.
 * Но такая таблица будет не совсем корректна.
 * Если вдруг у нас изменится название должности, придется обновлять все соответствующие записи в people.
 * Поэтому мы решаем изменить БД и организовать данные немного по-другому.
 * <p>
 * Во второй версии добавим таблицу position с названием должности и зарплатой.
 * И в таблице people вместо названия должности пропишем соответствующий ID из position.
 */
public class MainActivity extends AppCompatActivity {

    public static final String LOG_TAG = "myLogs";

    public static final String DB_NAME = "staff"; // имя БД
    public static final int DB_VERSION = 2; // версия БД

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        Log.d(LOG_TAG, "--- Staff db v." + database.getVersion() + " ---");
        writeStaff(database);
        dbHelper.close();
    }

    //    Запись данных в лог
    private void writeStaff(SQLiteDatabase database) {
        Cursor cursor = database.rawQuery("select * from people", null);
        logCursor(cursor, "Table people");
        cursor.close();

        cursor = database.rawQuery("select * from position", null);
        logCursor(cursor, "Table position");
        cursor.close();

        String sqlQuery = "select PL.name as Name, PS.name as Position, salary as Salary "
                + "from people as PL "
                + "inner join position as PS "
                + "on PL.posid = PS.id ";
        cursor = database.rawQuery(sqlQuery, null);
        logCursor(cursor, "inner join");
        cursor.close();
    }

    //    Вывод в лог данных из курсора
    private void logCursor(Cursor cursor, String title) {
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                Log.d(LOG_TAG, title + ". " + cursor.getCount() + " rows");
                StringBuilder stringBuilder = new StringBuilder();
                do {
                    stringBuilder.setLength(0);
                    for (String cn : cursor.getColumnNames()) {
                        stringBuilder.append(cn + " = " + cursor.getString(cursor.getColumnIndex(cn)) + "; ");
                    }
                    Log.d(LOG_TAG, stringBuilder.toString());
                } while (cursor.moveToNext());
            } else Log.d(LOG_TAG, title + ". Cursor is null");
        }
    }
}
