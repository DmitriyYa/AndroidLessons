package com.example.p33sharedpreferences;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
//http://startandroid.ru/ru/uroki/vse-uroki-spiskom/73-urok-33-hranenie-dannyh-preferences.html

//    В этом уроке:
//    - хранение данных с помощью Preferences

    EditText editText;
    Button btnSave;
    Button btnLoad;

    SharedPreferences sharedPreferences;

    final String SAVED_TEXT = "saved_text";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText) findViewById(R.id.etText);

        btnSave = (Button) findViewById(R.id.btnSave);
        btnSave.setOnClickListener(this);

        btnLoad = (Button) findViewById(R.id.btnLoad);
        btnLoad.setOnClickListener(this);

//        чтобы загрузка происходила автоматически при  открытии приложения и не надо было жать кнопки.
// Для этого метод loadText будем вызывать в onCreate.
        loadText();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

//      чтобы  сохранение происходило автоматически при  закрытии приложения и не надо было жать кнопки.
        saveText();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSave:
                saveText();
                break;
            case R.id.btnLoad:
                loadText();
                break;

        }
    }

    private void loadText() {

//        Сначала с помощью метода getPreferences получаем объект sharedPreferences класса SharedPreferences,
// который позволяет работать с данными (читать и писать).
// Константа MODE_PRIVATE используется для настройки доступа и означает, что после сохранения,
// данные будут видны только этому приложению.
        sharedPreferences=getPreferences(MODE_PRIVATE);

//        Читаем с помощью метода getString – в параметрах указываем константу - это имя,
// и значение по умолчанию (пустая строка).
        String savedText=sharedPreferences.getString(SAVED_TEXT, "");

        editText.setText(savedText);
        Toast.makeText(this,"Text loaded",Toast.LENGTH_SHORT).show();
    }

    private void saveText() {

//        //        Сначала с помощью метода getPreferences получаем объект sharedPreferences класса SharedPreferences,
// который позволяет работать с данными (читать и писать).
// Константа MODE_PRIVATE используется для настройки доступа и означает, что после сохранения,
// данные будут видны только этому приложению.
        sharedPreferences=getPreferences(MODE_PRIVATE);

//        чтобы редактировать данные, необходим объект Editor – получаем его из sharedPreferences.
        Editor editor=sharedPreferences.edit();

//        В метод putString указываем наименование переменной – это константа SAVED_TEXT,
// и значение – содержимое поля etText.
        editor.putString(SAVED_TEXT,editText.getText().toString());

//        Чтобы данные сохранились, необходимо выполнить commit.
        editor.commit();

        Toast.makeText(this,"TExt saved", Toast.LENGTH_SHORT).show();
    }


}
