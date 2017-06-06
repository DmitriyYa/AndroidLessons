package com.example.p29simpleactivityresult;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
//http://startandroid.ru/ru/uroki/vse-uroki-spiskom/68-urok-29-vyzyvaem-activity-i-poluchaem-rezultat-metod-startactivityforresult.html

    TextView tvName;
    Button btnGetName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvName= (TextView) findViewById(R.id.tvName);
        btnGetName= (Button) findViewById(R.id.btnGetName);
        btnGetName.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent=new Intent(this,NameActivity.class);

        /**
         * Для отправки используем startActivityForResult.
         * Отличие от обычного startActivity в том, что MainActivity становится «родителем» для NameActivity.
         * И когда NameActivity закрывается, вызывается метод onActivityResult в MainActivity,
         * тем самым давая нам знать, что закрылось Activity, которое мы вызывали методом startActivityForResult.
         */
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data==null){
            return;
        }

        String name=data.getStringExtra("name");
        tvName.setText("Получено имя: "+ name);
    }
}
