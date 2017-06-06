package com.example.p29simpleactivityresult;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NameActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnSetName;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name);

        btnSetName = (Button) findViewById(R.id.btnSetName);
        editText = (EditText) findViewById(R.id.etName);

        btnSetName.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        /**
         * В методе onClick мы создаем Intent и помещаем в него данные из поля ввода под именем name.
         * Обратите внимание, мы никак не адресуем этот Intent.
         * Т.е. ни класс, ни action мы не указываем.
         * И получается, что непонятно куда пойдет этот Intent.
         * Но метод setResult знает, куда его адресовать - в «родительское» Activity,
         * в котором был вызван метод startActivityForResult.
         * Также в setResult мы передаем константу RESULT_OK, означающую успешное завершение вызова.
         * И именно она передастся в параметр resultCode метода onActivityResult в MainActivity.java.
         */
        Intent intent=getIntent();
        intent.putExtra("name",editText.getText().toString());
        setResult(RESULT_OK,intent);
        finish();
    }
}
