package com.example.p34simplesqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    final static String LOG_TAG = "myLog";

    Button btnAdd;
    Button btnRead;
    Button btnClean;
    Button btnUpdate;
    Button btnDelete;

    EditText editTextName;
    EditText editTextEmail;
    EditText editId;


    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);

        btnRead = (Button) findViewById(R.id.btnRead);
        btnRead.setOnClickListener(this);

        btnClean = (Button) findViewById(R.id.btnClear);
        btnClean.setOnClickListener(this);

        btnUpdate = (Button) findViewById(R.id.btnUpd);
        btnUpdate.setOnClickListener(this);

        btnDelete = (Button) findViewById(R.id.btnDel);
        btnDelete.setOnClickListener(this);

        editTextName = (EditText) findViewById(R.id.etName);
        editTextEmail = (EditText) findViewById(R.id.etEmail);
        editId = (EditText) findViewById(R.id.etID);

        // создаем объект для создания и управления версиями БД
        dbHelper = new DBHelper(this);

    }

    @Override
    public void onClick(View v) {
        // создаем объект для данных
        //Класс ContentValues используется для указания полей таблицы и значений,
        // которые мы в эти поля будем вставлять.
        ContentValues contentValues = new ContentValues();

        // получаем данные из полей ввода
        String name = editTextName.getText().toString();
        String email = editTextEmail.getText().toString();
        String id = editId.getText().toString();

        // подключаемся к БД
        //с помощью метода getWritableDatabase подключаемся к БД и получаем объект SQLiteDatabase.
        // Он позволит нам работать с БД.
        // Мы будем использовать его методы
        // insert – вставка записи,
        // query – чтение,
        // delete – удаление.
        // У них много разных параметров на вход, но мы пока используем самый минимум.
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        switch (v.getId()) {
            case R.id.btnAdd:
                Log.d(LOG_TAG, "--- Insert in mytable: ---");

                // подготовим данные для вставки в виде пар: наименование столбца - значение
                //Мы заполняем объект cv парами: имя поля и значение.
                // И (при вставке записи в таблицу) в указанные поля будут вставлены соответствующие значения.
                // Мы заполняем поля name и email. id у нас заполнится автоматически (primary key autoincrement).
                contentValues.put("name", name);
                contentValues.put("email", email);

                // вставляем запись и получаем ее ID
                //Вызываем метод insert – передаем ему имя таблицы и объект cv с вставляемыми значениями.
                // Второй аргумент метода используется, при вставке в таблицу пустой строки.
                // Нам это сейчас не нужно, поэтому передаем null.
                // Метод insert возвращает ID вставленной строки, мы его сохраняем в rowID и выводим в лог.
                long rowId = db.insert("mytable", null, contentValues);
                Log.d(LOG_TAG, " row insert, ID: " + rowId);
                break;

            case R.id.btnRead:
                Log.d(LOG_TAG, "--- Rows in mytable: ---");

                // делаем запрос всех данных из таблицы mytable, получаем Cursor
                //Для чтения используется метод query.
                // На вход ему подается имя таблицы, список запрашиваемых полей, условия выборки, группировка, сортировка.
                // Т.к. нам нужны все данные во всех полях без сортировок и группировок - мы используем везде null.
                // Только имя таблицы указываем. Метод возвращает нам объект класса Cursor.
                // Его можно рассматривать как таблицу с данными.
                Cursor cursor = db.query("mytable", null, null, null, null, null, null);

                // ставим позицию курсора на первую строку выборки
                // если в выборке нет строк, вернется false
                //Метод moveToFirst – делает первую запись в Cursor активной и заодно проверяет,
                // есть ли вообще записи в нем (т.е. выбралось ли что-либо в методе query).
                if (cursor.moveToFirst()) {

                    // определяем номера столбцов по имени в выборке
                    //мы получаем порядковые номера столбцов в Cursor по их именам с помощью метода getColumnIndex.
                    // Эти номера потом используем для чтения данных в методах getInt и getString и
                    // выводим данные в лог.
                    int idColIndex = cursor.getColumnIndex("id");
                    int nameColIndex = cursor.getColumnIndex("name");
                    int emailColIndex = cursor.getColumnIndex("email");

                    do {
                        // получаем значения по номерам столбцов и пишем все в лог
                        Log.d(LOG_TAG,
                                "ID = " + cursor.getInt(idColIndex) +
                                        ", name = " + cursor.getString(nameColIndex) +
                                        ", email = " + cursor.getString(emailColIndex));
                        // переход на следующую строку
                        // а если следующей нет (текущая - последняя), то false - выходим из цикла
                    }

                    //С помощью метода moveToNext мы перебираем все строки в Cursor пока не добираемся до последней.
                    // Если же записей не было, то выводим в лог соответствующее сообщение – 0 rows.
                    while (cursor.moveToNext());
                } else Log.d(LOG_TAG, " 0 rows");
                cursor.close();
                break;

            case R.id.btnClear:
                Log.d(LOG_TAG, "--- Clear mytable: ---");

                // удаляем все записи
                //Метод delete удаляет записи.
                // На вход передаем имя таблицы и null в качестве условий для удаления а значит удалится все.
                // Метод возвращает кол-во удаленных записей.
                int clearCount = db.delete("mytable", null, null);
                Log.d(LOG_TAG, "deleted rows count = " + clearCount);
                break;
            case R.id.btnUpd:
                if (id.equalsIgnoreCase("")) {
                    break;
                }
                Log.d(LOG_TAG, "--- Update mytable: ---");

                // подготовим значения для обновления
                contentValues.put("name", name);
                contentValues.put("email", email);

                // обновляем по id
                //Для этого используется метод update.
                // На вход ему подается имя таблицы, заполненный ContentValues с значениями для обновления,
                // строка условия (Where) и массив аргументов для строки условия.
                // В строке условия я использовал знак ?.
                // При запросе к БД вместо этого знака будет подставлено значение из массива аргументов,
                // в нашем случае это – значение переменной id.
                // Если знаков ? в строке условия несколько, то им будут сопоставлены значения из массива по порядку.
                // Метод update возвращает нам кол-во обновленных записей, которое мы выводим в лог.
                int updateCount = db.update("mytable", contentValues, "id=?", new String[]{id});
                Log.d(LOG_TAG, "updated rows count = " + updateCount);
                break;

            case R.id.btnDel:
                if (id.equalsIgnoreCase("")) {
                    break;
                }
                Log.d(LOG_TAG, "--- Delete from mytable: ---");

                // удаляем по id
                //Метод delete возвращает кол-во удаленных строк, которое мы выводим в лог.
                int delCount = db.delete("mytable", "id= " + id, null);
                //Обратите внимание, что условия и для update и для delete у меня одинаковые,
                // а именно id = значение из поля etID. Но реализовал я их немного по-разному.
                // Для update использовал символ ? в строке условия и массив аргументов.
                // А для delete вставил значение сразу в строку условия.
                // Таким образом, я просто показал способы формирования условия.
                // А вы уже используйте тот, что больше нравится или лучше в конкретной ситуации.

                Log.d(LOG_TAG, "deleted rows count = " + delCount);
                break;
        }
        // закрываем подключение к БД
        dbHelper.close();


    }
}
