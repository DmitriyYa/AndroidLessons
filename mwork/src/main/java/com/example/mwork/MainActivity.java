package com.example.mwork;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, LoaderManager.LoaderCallbacks<Cursor> {


    private static final int ITEM_CONTEXT_MENU_DELETE = 1;

    FloatingActionButton fab;
    ListView lvData;

    Intent intent;

    DB db;
    SQLiteDatabase mDB;
    SimpleCursorAdapter scAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // открываем подключение к БД
        db = new DB(this);
        mDB = db.open();


        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);

        // формируем столбцы сопоставления
        String[] from = new String[]{DB.COLUMN_SHOT_DESC, DB.COLUMN_TIME, DB.COLUMN_DATE, DB.COLUMN_STATUS};
        int[] to = new int[]{R.id.tvShortDecription, R.id.tvTime, R.id.tvDate, R.id.tvStatus};

        // создаем адаптер и настраиваем список
        scAdapter = new SimpleCursorAdapter(this, R.layout.item_list_activity, null, from, to, 0);
        lvData = (ListView) findViewById(R.id.lvData);
        lvData.setAdapter(scAdapter);

        // добавляем контекстное меню к списку
        registerForContextMenu(lvData);

        // создаем лоадер для чтения данных
        getSupportLoaderManager().initLoader(0, null, this);


        //обрабатываем нажатие элемента в сиписке
        lvData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Cursor cursor = db.getRec(id);
                intent = new Intent(getApplicationContext(), ViewJobActivity.class);

                // ставим позицию курсора на первую строку выборки
                // если в выборке нет строк, вернется false
                if (cursor.moveToFirst()) {

                    // определяем номера столбцов по имени в выборке
                    int _id = cursor.getColumnIndex(DB.COLUMN_ID);
                    int shortDesc = cursor.getColumnIndex(DB.COLUMN_SHOT_DESC);
                    int longDesc = cursor.getColumnIndex(DB.COLUMN_LONG_DESC);
                    int myTime = cursor.getColumnIndex(DB.COLUMN_TIME);
                    int myDate = cursor.getColumnIndex(DB.COLUMN_DATE);
                    int myStatus = cursor.getColumnIndex(DB.COLUMN_STATUS);

                    do {
                        // получаем значения по номерам столбцов и пишем все в intent
                        intent.putExtra(DB.COLUMN_ID, cursor.getString(_id));
                        intent.putExtra(DB.COLUMN_SHOT_DESC, cursor.getString(shortDesc));
                        intent.putExtra(DB.COLUMN_LONG_DESC, cursor.getString(longDesc));
                        intent.putExtra(DB.COLUMN_TIME, cursor.getString(myTime));
                        intent.putExtra(DB.COLUMN_DATE, cursor.getString(myDate));
                        intent.putExtra(DB.COLUMN_STATUS, cursor.getString(myStatus));

                        // переход на следующую строку
                        // а если следующей нет (текущая - последняя), то false - выходим из цикла
                    } while (cursor.moveToNext());
                }
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        // получаем новый курсор с данными
        getSupportLoaderManager().getLoader(0).forceLoad();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                intent = new Intent(this, CreateJobActivity.class);
                startActivityForResult(intent, 1);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        if (resultCode == RESULT_OK) {
            // получаем новый курсор с данными
            getSupportLoaderManager().getLoader(0).forceLoad();

            if (requestCode == 1) {
                Toast.makeText(this, "Задача создана", Toast.LENGTH_SHORT).show();
            }
            if (requestCode == 2) {
                Toast.makeText(this, "Задача обновлена", Toast.LENGTH_SHORT).show();
            }
        } else if (resultCode == RESULT_CANCELED) {
            if (requestCode == 1) {
                Toast.makeText(this, "Задача не создана", Toast.LENGTH_SHORT).show();
            }
            if (requestCode == 2) {
                Toast.makeText(this, "Задача не обновлена", Toast.LENGTH_SHORT).show();
            }
        }

    }

    //вызывается только при первом показе меню.
    //Создает меню и более не используется. Здесь мы добавляем к меню пункты.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        //С помощью метода getMenuInflater мы получаем MenuInflater и вызываем его метод inflate.
        //На вход передаем наш файл mymenu.xml из папки res/menu и объект menu.
        //MenuInflater берет объект menu и наполняет его пунктами согласно файлу
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);

    }


    //вызывается при нажатии пункта меню.
    //Здесь мы определяем какой пункт меню был нажат.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
                // TODO: 23.06.17 реализовать активити Настройки
//                intent = new Intent(this, AnalisisActivity.class);
//                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    //Создание контекстного меню
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, ITEM_CONTEXT_MENU_DELETE, 0, "удалить");
    }


    //В этом методе мы определяем по ID, какой пункт меню был нажат.
    //И выполняем соответствующие действия: меняем цвет текста для tvColor или размер шрифта для tvSize.
    //Сохраняем, запускаем и проверяем, что контекстные меню теперь реагируют на нажатия и делают то,
    //что от них требуется.
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case ITEM_CONTEXT_MENU_DELETE:
                // получаем из пункта контекстного меню данные по пункту списка
                AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                // извлекаем id записи и удаляем соответствующую запись в БД
                db.delRec(acmi.id);
                // получаем новый курсор с данными
                getSupportLoaderManager().getLoader(0).forceLoad();
                break;
        }
        return super.onContextItemSelected(item);
    }

    protected void onDestroy() {
        super.onDestroy();
        // закрываем подключение при выходе
        db.close();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new MyCursorLoader(this, db);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        scAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }


    static class MyCursorLoader extends CursorLoader {

        DB db;

        public MyCursorLoader(Context context, DB db) {
            super(context);
            this.db = db;
        }

        @Override
        public Cursor loadInBackground() {
            Cursor cursor = db.getAllData();
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return cursor;
        }
    }

}
