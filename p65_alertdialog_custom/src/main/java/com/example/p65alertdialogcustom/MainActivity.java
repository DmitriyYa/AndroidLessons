package com.example.p65alertdialogcustom;

import android.app.AlertDialog;
import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.ViewGroup.LayoutParams;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

//В этом уроке:
//- используем свои View для построения диалога

//Нарисуем приложение, которое будет использовать свой View-компонент в теле диалога и рассмотрим,
//как можно модифицировать содержимое этого компонента. Будем добавлять и удалять TextView в теле диалога.

public class MainActivity extends AppCompatActivity {

    final int DIALOG = 1;

    int btn;
    LinearLayout view;
    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
    TextView tvCount;
    ArrayList<TextView> textViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViews = new ArrayList<TextView>(10);
    }

    public void onclick(View v) {
        btn = v.getId();
        showDialog(DIALOG);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        adb.setTitle("Custom dialog");
        // создаем view из dialog.xml
        view = (LinearLayout) getLayoutInflater()
                .inflate(R.layout.dialog, null);
        // устанавливаем ее, как содержимое тела диалога
        adb.setView(view);
        // находим TexView для отображения кол-ва
        tvCount = (TextView) view.findViewById(R.id.tvCount);
        return adb.create();
    }

    //Обратите внимание, что я использую два разных способа для получения доступа к tvCount и tvTime.
    //tvCount я нашел сразу после создания view в методе onCreateDialog.
    //А в случае с tvTime я показываю, как найти View-компонент в диалоге без использования объекта view.
    //Какой вам удобнее по ситуации, тот и используйте.
    @Override
    protected void onPrepareDialog(int id, Dialog dialog) {
        super.onPrepareDialog(id, dialog);
        if (id == DIALOG) {
            // Находим TextView для отображения времени и показываем текущее время
            TextView tvTime = (TextView) dialog.getWindow().findViewById(R.id.tvTime);
            tvTime.setText(sdf.format(new Date(System.currentTimeMillis())));
            // если была нажата кнопка Добавить
            if (btn == R.id.btnAdd) {
                // создаем новое TextView, добавляем в диалог, указываем текст
                TextView tv = new TextView(this);
                view.addView(tv, new LayoutParams(LayoutParams.MATCH_PARENT,
                        LayoutParams.WRAP_CONTENT));
                tv.setText("TextView " + (textViews.size() + 1));
                // добавляем новое TextView в коллекцию
                textViews.add(tv);
                // иначе
            } else {
                // если коллекция созданных TextView непуста
                if (textViews.size() > 0) {
                    // находим в коллекции последний TextView
                    TextView tv = textViews.get(textViews.size() - 1);
                    // удаляем из диалога
                    view.removeView(tv);
                    // удаляем из коллекции
                    textViews.remove(tv);
                }
            }
            // обновляем счетчик
            tvCount.setText("Кол-во TextView = " + textViews.size());
        }
    }
}
