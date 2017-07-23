package com.example.p69parcelable;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
//В этом уроке:
//- добавляем объекту поддержку Parcelable
//- передаем с помощью Intent

//Итак. Чтобы нам передать объект через Intent, нам надо реализовать в нем интерфейс Parcelable.
//В этом случае Intent без проблем запакует, передаст и распакует наш объект.
//И я так подозреваю, что делает он это с помощью Parcel.
//Т.е. в реализации интерфейса Parcelable мы полностью описываем алгоритм упаковки и распаковки объекта,
//а Parcel эти алгоритмы потом использует.
//Т.к. сам он не может знать, как правильно распаковать и создать передаваемый объект.
public class MainActivity extends AppCompatActivity {

    final String LOG_TAG = "myLogs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onclick(View view){
        MyObject myObject=new MyObject("text",1);

        //Создаем Intent, помещаем туда объект MyObject.
        //В качестве ключа используем его имя класса (разумеется, это необязательно, вы можете свое имя использовать).
        //И отправляем Intent с вызовом SecondActivity.
        Intent intent=new Intent(this,SecondActivity.class);
        intent.putExtra(MyObject.class.getCanonicalName(),myObject);
        Log.d(LOG_TAG,"startActivity");
        startActivity(intent);
    }
}
