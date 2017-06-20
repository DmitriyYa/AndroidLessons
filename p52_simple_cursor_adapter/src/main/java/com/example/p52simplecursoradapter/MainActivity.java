package com.example.p52simplecursoradapter;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.AdapterView.AdapterContextMenuInfo;

//http://startandroid.ru/ru/uroki/vse-uroki-spiskom/111-urok-52-simplecursoradapter-primer-ispolzovanija.html

//В этом уроке:
//- используем SimpleCursorAdapter для построения списка
//- добавляем и удаляем записи в списке

//Итак, давайте накидаем пример использования SimpleCursorAdapter.
//Список будет отображать картинку и текст.
//Также реализуем возможность добавления и удаления данных из списка.
//Добавлять будем кнопкой, а удалять с помощью контекстного меню.

public class MainActivity extends AppCompatActivity {

    private static final int CM_DELETE_ID = 1;
    ListView lvData;
    DB db;
    SimpleCursorAdapter scAdapter;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //открываем подключение к базе данных
        db = new DB(this);
        db.open();

//        получаем cursor
        cursor = db.getAllData();
//        получаем курсор и просим Activity присмотреть за ним.
//        Теперь при смене Lifecycle-состояний Activity, оно будет менять соответствующим образом состояния курсора.
        startManagingCursor(cursor);

//        формируем столбцы сопоставления
//        Затем настраиваем биндинг – формируем массивы, которые укажут адаптеру,
// как сопоставлять данные из курсора и View-компоненты.
        String[] from = new String[]{DB.COLUMN_IMG, DB.COLUMN_TXT};
        int[] to = new int[]{R.id.ivImg, R.id.lvData};

//        создаем адаптер и настраиваем списки
        scAdapter = new SimpleCursorAdapter(this, R.layout.item, cursor, from, to);
        lvData = (ListView) findViewById(R.id.lvData);
        lvData.setAdapter(scAdapter);

//        добавляем контекстое меню к списку
        registerForContextMenu(lvData);
    }

    //    обработка нажания кнопки
    public void onButtonClick(View view) {
        // добавляем запись
        db.addRec("sometext " + (cursor.getCount() + 1), R.mipmap.ic_launcher);
        // обновляем курсор
        cursor.requery();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
//        добавляем пункт для удаления.
        menu.add(0, CM_DELETE_ID, 0, R.string.delete_record);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId() == CM_DELETE_ID) {
            // получаем из пункта контекстного меню данные по пункту списка
//            Чтобы получить данные по пункту списка, для  которого был совершен вызов контекстного меню,
// мы используем метод getMenuInfo.
// Объект AdapterContextMenuInfo содержит данные о View, id и позиции пункта списка.
            AdapterContextMenuInfo acmi = (AdapterContextMenuInfo) item.getMenuInfo();
            // извлекаем id записи и удаляем соответствующую запись в БД
            db.delRec(acmi.id);
            // обновляем курсор
            cursor.requery();
            return true;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // закрываем подключение при выходе
        db.close();
    }
}
