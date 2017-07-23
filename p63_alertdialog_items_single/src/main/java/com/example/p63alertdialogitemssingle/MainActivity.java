package com.example.p63alertdialogitemssingle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

//В этом уроке:
//- формируем список с одиночным выбором в диалоге

public class MainActivity extends AppCompatActivity {

    final String LOG_TAG = "myLogs";

    final int DIALOG_ITEMS = 1;
    final int DIALOG_ADAPTER = 2;
    final int DIALOG_CURSOR = 3;
    DB db;
    Cursor cursor;

    String data[] = { "one", "two", "three", "four" };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // открываем подключение к БД
        db = new DB(this);
        db.open();
        cursor = db.getAllData();
        startManagingCursor(cursor);
    }

    public void onclick(View v) {
        switch (v.getId()) {
            case R.id.btnItems:
                showDialog(DIALOG_ITEMS);
                break;
            case R.id.btnAdapter:
                showDialog(DIALOG_ADAPTER);
                break;
            case R.id.btnCursor:
                showDialog(DIALOG_CURSOR);
                break;
            default:
                break;
        }
    }

    protected Dialog onCreateDialog(int id) {
        //onCreateDialog – создаем вызываемый диалог, используя AlertDialog.Builder.
        //Диалог может построить список, используя один из следующих объектов:
        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        switch (id) {
            //1) Массив строк.
            //Используется метод setSingleChoiceItems.
            //На вход подается массив, позиция выбранного элемента и обработчик нажатия.
            //Если мы в значение выбранного элемента передаем -1, то в списке не будет выбранного элемента.
            case DIALOG_ITEMS:
                adb.setTitle(R.string.items);
                adb.setSingleChoiceItems(data, -1, myClickListener);
                break;
            //2) Адаптер.
            //Мы создаем ArrayAdapter, используя массив data и стандартный layout select_dialog_singlechoice,
            //и передаем его в метод setSingleChoiceItems.
            //Также передаем туда позицию выбранного элемента и обработчик.
            case DIALOG_ADAPTER:
                adb.setTitle(R.string.adapter);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                        android.R.layout.select_dialog_singlechoice, data);
                adb.setSingleChoiceItems(adapter, -1, myClickListener);
                break;
            //3) Курсор БД.
            //Вызываем метод setSingleChoiceItems.
            //Передаем туда курсор, позицию выбранного элемента, имя поля (значение которого будет показано в списке)
            //и обработчик нажатия.
            case DIALOG_CURSOR:
                adb.setTitle(R.string.cursor);
                adb.setSingleChoiceItems(cursor, -1, DB.COLUMN_TXT, myClickListener);
                break;
        }
        //Кроме списка указываем только заголовок.
        //В конце добавим кнопку ОК, создаем и возвращаем Dialog.
        adb.setPositiveButton(R.string.ok, myClickListener);
        return adb.create();
    }

    //Если теперь снова откроете диалог, то выбранный пункт так и останется выбранным.
    //Т.к. диалог не уничтожается при закрытии.
    //Если хотите перед каждым вызовом менять выбранный элемент, реализуйте метод onPrepareDialog.
    //В нем надо добраться до списка и вызвать метод setItemChecked.
    //Пример кода, в котором выбирается третий элемент (нумерация с нуля):

    @Override
    protected void onPrepareDialog(int id, Dialog dialog) {
        ((AlertDialog) dialog).getListView().setItemChecked(2, true);
    }

    // обработчик нажатия на пункт списка диалога или кнопку
    OnClickListener myClickListener = new OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
            ListView lv = ((AlertDialog) dialog).getListView();
            if (which == Dialog.BUTTON_POSITIVE)
                // выводим в лог позицию выбранного элемента
                Log.d(LOG_TAG, "pos = " + lv.getCheckedItemPosition());
            else
                // выводим в лог позицию нажатого элемента
                Log.d(LOG_TAG, "which = " + which);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }
}
