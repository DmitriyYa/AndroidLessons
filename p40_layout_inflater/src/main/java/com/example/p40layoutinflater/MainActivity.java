package com.example.p40layoutinflater;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    final String LOG_TAG = "myLogs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        //Мы получаем LayoutInflater методом getLayoutInflater,
//        // используем его для получения View-элемента из layout-файла text.xml
//        // и считываем LayoutParams у свежесозданного view.
//        LayoutInflater layoutInflater = getLayoutInflater();
//        View view = layoutInflater.inflate(R.layout.text, null, false);
//        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
//
//        //Мы нашли linLayout с экрана и добавили в него созданный с помощью LayoutInflater элемент.
//        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linLayout);
//        linearLayout.addView(view);
//
//        Log.d(LOG_TAG, "Class of view: " + view.getClass().toString());
//        Log.d(LOG_TAG, "LayoutParams of view is null: " + (layoutParams == null));
//        Log.d(LOG_TAG, "Text of view: " + ((TextView) view).getText());


//Теперь давайте попробуем указать родителя (root) при вызове метода inflate. Перепишем метод onCreate:
        LayoutInflater layoutInflater = getLayoutInflater();

        //Мы находим элементы linLayout и relLayout с экрана
        LinearLayout linLayout = (LinearLayout) findViewById(R.id.linLayout);

        //и с помощью LayoutInflater создаем два View-элемента из layout-файла text.xml.
        // Для первого указываем root – linLayout, для второго – relLayout.
        // Но третий параметр attachToRoot оставляем false.
        // Это значит, что созданный View-элемент получит LayoutParams от root-элемента,
        // но не добавится к нему.
        View view1 = layoutInflater.inflate(R.layout.text, linLayout, true);
        ViewGroup.LayoutParams lp1 = view1.getLayoutParams();

        Log.d(LOG_TAG, "Class of view1: " + view1.getClass().toString());
        Log.d(LOG_TAG, "Class of layoutParams of view1: " + lp1.getClass().toString());
//        Log.d(LOG_TAG, "Text of view1: " + ((TextView) view1).getText());

        RelativeLayout relLayout = (RelativeLayout) findViewById(R.id.relLayout);
        View view2 = layoutInflater.inflate(R.layout.text, relLayout, true);
        ViewGroup.LayoutParams lp2 = view2.getLayoutParams();

        Log.d(LOG_TAG, "Class of view2: " + view2.getClass().toString());
        Log.d(LOG_TAG, "Class of layoutParams of view2: " + lp2.getClass().toString());
//        Log.d(LOG_TAG, "Text of view2: " + ((TextView) view2).getText());

        //Все сохраним, запустим. На экране ничего не поменялось.
        // Т.к. мы ни к чему новые элементы не добавляли и attachToRoot = false.
    }
}
