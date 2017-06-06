package com.example.p32simplebrowser;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
//    http://startandroid.ru/ru/uroki/vse-uroki-spiskom/72-urok-32-pishem-prostoj-brauzer.html

// В этом уроке:
//    - пишем простой браузер

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button= (Button) findViewById(R.id.button);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.ya.ru"));
        startActivity(intent);
    }
}
