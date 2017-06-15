package com.example.p37sqliteinnerjoin;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import static com.example.p37sqliteinnerjoin.DBHelper.TABLE_PEOPLE;
import static com.example.p37sqliteinnerjoin.DBHelper.TABLE_POSITION;
//    http://startandroid.ru/ru/uroki/vse-uroki-spiskom/77-urok-37-zaprosy-iz-svjazannyh-tablits-inner-join-v-sqlite-metod-rawquery.html

//    В этом уроке:
//    - читаем данные из связанных таблиц
//    - используем rawQuery

public class MainActivity extends AppCompatActivity {

    final static String LOG_TAG = "myLogs";

    // данные для таблицы должностей
    public static int[] position_id = {1, 2, 3, 4};
    public static String[] position_name = {"Директор", "Программер", "Бухгалтер", "Охранник"};
    public static int[] position_salary = {15000, 13000, 10000, 8000};

    // данные для таблицы людей
    public static String[] people_name = {"Иван", "Марья", "Петр", "Антон", "Даша", "Борис", "Костя", "Игорь"};
    public static int[] people_posid = {2, 3, 2, 2, 3, 1, 2, 4};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //подключаемся к БД
        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        //Описание курсора
        Cursor cursor;

        //выводим в лог данные по должностям
        Log.d(LOG_TAG, "--- Table position ---");
        cursor = database.query(TABLE_POSITION, null, null, null, null, null, null);
        logCursor(cursor);
        cursor.close();
        Log.d(LOG_TAG, "--- ---");

        //выводим в лог данные по людям
        Log.d(LOG_TAG, "--- Tabe people ---");
        cursor = database.query(TABLE_PEOPLE, null, null, null, null, null, null);
        logCursor(cursor);
        cursor.close();
        Log.d(LOG_TAG, "--- ---");

        //выводим результат объединения
        //используем rawQuery
        //Для вывода объединения таблиц используем rawQuery.
        // Это несложный метод, который принимает на вход SQL-запрос и список аргументов для условия WHERE (если необходимо).
        // Мы сформировали запрос на объединение двух таблиц и вывода имени, должности и зарплаты человека.
        // Условие выборки: ЗП должна быть больше 12000.
        // Мы использовали аргументы для формирования условия.
        Log.d(LOG_TAG, "--- INNER JOIN with rowQuery ---");
        String sqlQuery = " select PL.name as Name, PS.posit as Position, salary as Salary "
                + "from people as PL "
                + "inner join position as PS "
                + "on PL.posid = PS.id "
                + "where salary > ?";
        cursor = database.rawQuery(sqlQuery, new String[]{"12000"});
        logCursor(cursor);
        cursor.close();
        Log.d(LOG_TAG, "--- ---");

        //выводим результат объединения
        //используем query
        //Далее снова выводим объединение таблиц, но используем обычный query.
        // В table записываем все таблицы, их алиасы и условие JOIN.
        // В columns – все нужные поля с использованием алиасов.
        // Ну и в selection и selectionArgs пишем условие выборки – ЗП меньше 12000.
        Log.d(LOG_TAG, "--- INNER JOIN with query ---");
        String table = "people as PL inner join position as PS on PL.posid = PS.id";
        String[] colunms = {"PL.name as Name", "PS.posit as Position", "salary as Salary"};
        String selection = "salary < ?";
        String[] selectionArgs = {"12000"};
        cursor = database.query(table, colunms, selection, selectionArgs, null, null, null);
        logCursor(cursor);
        cursor.close();
        Log.d(LOG_TAG, "--- ---");

        //закрываем БД
        dbHelper.close();

    }

    //вывод в лог данных из курсора
    private void logCursor(Cursor cursor) {
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                String string;
                do {
                    string = "";
                    for (String cn : cursor.getColumnNames()) {
                        string = string.concat(cn + " = " + cursor.getString(cursor.getColumnIndex(cn)) + "; ");
                    }
                    Log.d(LOG_TAG, string);
                }
                while (cursor.moveToNext());
            }
        } else Log.d(LOG_TAG, "Cursor is null");
    }
}
