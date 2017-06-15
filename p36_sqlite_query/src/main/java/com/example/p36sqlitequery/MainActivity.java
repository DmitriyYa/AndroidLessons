package com.example.p36sqlitequery;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
//    http://startandroid.ru/ru/uroki/vse-uroki-spiskom/76-urok-36-sqlite-podrobnee-pro-metod-query-uslovie-sortirovka-gruppirovka.html

//    В этом уроке:
//    - подробно разбираем метод чтения данных query
//    - используем сортировку, группировку, условия, having

    //    у query есть параметры:
//    columns – список полей, которые мы хотим получить
//    selection – строка условия WHERE
//    selectionArgs – массив аргументов для selection. В selection можно использовать знаки ?, которые будут заменены этими значениями.
//    groupBy - группировка
//    having – использование условий для агрегатных функций
//    orderBy - сортировка

    final static String LOG_TAG = "myLog";
    final static String MY_TABLE = "mytable";

    String[] name = {"Китай", "США", "Бразилия", "Россия", "Япония", "Германия", "Египет", "Италия", "Франция", "Канада"};
    int[] people = {1400, 311, 195, 142, 128, 82, 80, 60, 66, 35};
    String[] region = {"Азия", "Америка", "Америка", "Европа", "Азия", "Европа", "Африка", "Европа", "Европа", "Америка"};

    Button btnAll;
    Button btnFunc;
    Button btnPeople;
    Button btnSort;
    Button btnGroup;
    Button btnHaving;

    EditText etFunc;
    EditText etPeople;
    EditText etRegionPeople;

    RadioGroup rgSort;

    // создаем объект для создания и управления версиями БД
    DBHelper dbHelper;


    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAll = (Button) findViewById(R.id.btnAll);
        btnAll.setOnClickListener(this);

        btnFunc = (Button) findViewById(R.id.btnFunc);
        btnFunc.setOnClickListener(this);

        btnPeople = (Button) findViewById(R.id.btnPeople);
        btnPeople.setOnClickListener(this);

        btnSort = (Button) findViewById(R.id.btnSort);
        btnSort.setOnClickListener(this);

        btnGroup = (Button) findViewById(R.id.btnGroup);
        btnGroup.setOnClickListener(this);

        btnHaving = (Button) findViewById(R.id.btnHaving);
        btnHaving.setOnClickListener(this);


        etFunc = (EditText) findViewById(R.id.etFunc);
        etPeople = (EditText) findViewById(R.id.etPeople);
        etRegionPeople = (EditText) findViewById(R.id.etRegionPeople);

        rgSort = (RadioGroup) findViewById(R.id.rgSort);

        dbHelper = new DBHelper(this);

        // подключаемся к базе
        database = dbHelper.getWritableDatabase();

        // проверка существования записей
        Cursor cursor = database.query(MY_TABLE, null, null, null, null, null, null);
        if (cursor.getCount() == 0) {
            ContentValues contentValues = new ContentValues();

            //заполняем таблицу
            for (int i = 0; i < 10; i++) {
                contentValues.put("name", name[i]);
                contentValues.put("people", people[i]);
                contentValues.put("region", region[i]);
                Log.d(LOG_TAG, "id =" + database.insert(MY_TABLE, null, contentValues));
            }
        }
        cursor.close();
        dbHelper.close();

        // эмулируем нажатие кнопки btnAll
        onClick(btnAll);
    }

    @Override
    public void onClick(View v) {

        // подключаемся к базе
        database = dbHelper.getWritableDatabase();

        // данные с экрана
        String sFunc = etFunc.getText().toString();
        String sPeople = etPeople.getText().toString();
        String sRegionPeople = etRegionPeople.getText().toString();

//        переменные для query
        String[] colunms = null;
        String selection = null;
        String[] selectionArgs = null;
        String groupBy = null;
        String having = null;
        String orderBy = null;

//        курсор
        Cursor cursor = null;

//        определяем нажатую кнопку
        switch (v.getId()) {

            //все записи
            //btnAll – вывод всех записей.
            // Вызываем метод query с именем таблицы и null для остальных параметров.
            // Это уже знакомо, делали на прошлом уроке.
            case R.id.btnAll:
                Log.d(LOG_TAG, "--- Все записи ---");
                cursor = database.query(MY_TABLE, null, null, null, null, null, null);
                break;

            //функция
            //btnFunc – вывод значения агрегатной функции (или любого поля).
            // Использую параметр columns, в который надо записать поля, которые я хотел бы получить из таблицы,
            // т.е. то, что обычно перечисляется после слова SELECT в SQL-запросе.
            // columns имеет тип String[] – массив строк.
            // Создаем массив из одного значения, которое считано с поля etFunc на экране.
            // Запускаем query.
            case R.id.btnFunc:
                Log.d(LOG_TAG, "--- Функция " + sFunc + " ---");
                colunms = new String[]{sFunc};
                cursor = database.query(MY_TABLE, colunms, null, null, null, null, null);
                break;

            //Население больше, чем
            //btnPeople – вывод стран с населением больше введенного на экране количества.
            // Используем selection для формирования условия.
            // При этом используем один аргумент - ?.
            // Значение аргумента задаем в selectionArgs – это sPeople – содержимое поля etPeople.
            // Запускаем query.
            case R.id.btnPeople:
                Log.d(LOG_TAG, "--- Население больше " + sPeople + " ---");
                selection = "people > ?";
                selectionArgs = new String[]{sPeople};
                cursor = database.query(MY_TABLE, null, selection, selectionArgs, null, null, null);
                break;

            //Население по региону
            //btnGroup – группировка стран по регионам и вывод общее количество населения.
            // Используем columns для указания столбцов, которые хотели бы получить – регион и сумма населения.
            // В groupBy указываем, что группировка будет по региону.
            // Запускаем query.
            case R.id.btnGroup:
                Log.d(LOG_TAG, "--- Население по региону ---");
                colunms = new String[]{"region", "sum(people) as people"};
                groupBy = "region";
                cursor = database.query(MY_TABLE, colunms, null, null, groupBy, null, null);
                break;

            //Население по региону больше чем
            //btnHaving – вывод регионов с населением больше указанного числа.
            // Полностью аналогично случаю с группировкой,
            // но добавляется условие в параметре having – сумма населения региона должна быть
            // меньше sRegionPeople (значение etRegionPeople с экрана).
            case R.id.btnHaving:
                Log.d(LOG_TAG, "--- Регионы с населением больше " + sRegionPeople + " ---");
                colunms = new String[]{"region", "sum(people) as people"};
                groupBy = "region";
                having = "sum(people) >" + sRegionPeople;
                cursor = database.query(MY_TABLE, colunms, null, null, groupBy, having, null);
                break;

            //Сортировка
            //btnSort – сортировка стран.
            // Определяем какой RadioButton включен и соответственно указываем в orderBy поле для сортировки данных.
            // Запускаем query.
            case R.id.rgSort:

//                сортировка по
                switch (rgSort.getCheckedRadioButtonId()) {
//                    наименованию
                    case R.id.rName:
                        Log.d(LOG_TAG, "--- Сортировка по наименованию ---");
                        orderBy = "name";
                        break;

//                    населению
                    case R.id.rPeople:
                        Log.d(LOG_TAG, "--- Сортировка по населению ---");
                        orderBy = "people";
                        break;

//                    региону
                    case R.id.rRegion:
                        Log.d(LOG_TAG, "--- Сортировка по региону ---");
                        orderBy = "region";
                        break;
                }
                cursor = database.query(MY_TABLE, null, null, null, null, null, orderBy);
                break;
        }

        //В выше описанных случаях мы запускали query и получали объект класса Cursor.
        if (cursor != null) {

            //Далее мы проверяем, что он существует и в нем есть записи (moveToFirst).
            if (cursor.moveToFirst()) {
                String string;

                //Если все ок, то мы запускаем перебор записей в цикле do … while (c.moveToNext()).
                do {
                    string = "";

                    //Для каждой записи перебираем названия полей (getColumnNames),
                    // получаем по каждому полю его номер и извлекаем данные методом getString.
                    for (String cn : cursor.getColumnNames()) {
                        string = string.concat(cn + " = " + cursor.getString(cursor.getColumnIndex(cn)) + "; ");
                    }
                    Log.d(LOG_TAG, string);
                }
                while (cursor.moveToNext());

            }
            cursor.close();
        } else Log.d(LOG_TAG, "Cursot is null");

        dbHelper.close();
    }
}
