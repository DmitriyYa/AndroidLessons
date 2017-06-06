package com.example.p28extrastransferringdatausingintent;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

//    http://startandroid.ru/ru/uroki/vse-uroki-spiskom/67-urok-28-extras-peredaem-dannye-s-pomoschju-intent.html

    EditText editTextFName;
    EditText editTextLName;
    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextFName = (EditText) findViewById(R.id.editTextfirstName);
        editTextLName = (EditText) findViewById(R.id.editTextLastName);
        btnSubmit = (Button) findViewById(R.id.submit);

        btnSubmit.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {

        Intent intent = new Intent(this, ViewActivity.class);

//        Используется метод putExtra.
// Он имеет множество вариаций и аналогичен методу put для Map, т.е. добавляет к объекту пару.
// Первый параметр – это ключ(имя), второй - значение.
        intent.putExtra("fname", editTextFName.getText().toString());
        intent.putExtra("lname", editTextLName.getText().toString());
        startActivity(intent);
    }
}
