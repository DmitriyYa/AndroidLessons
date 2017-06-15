package com.example.p42simplelist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

//http://startandroid.ru/ru/uroki/vse-uroki-spiskom/82-urok42-spisok-listview.html

//В этом уроке:
//- используем ListView для построения списка

//При создании ListView создавать пункты за нас будет адаптер.
// Адаптеру нужны от нас данные и layout-ресурс пункта списка.
// Далее мы присваиваем адаптер списку ListView.
// Список при построении запрашивает у адаптера пункты,
// адаптер их создает (используя данные и layout) и возвращает списку.
// В итоге мы видим готовый список.

public class MainActivity extends AppCompatActivity {

    String[] names = {"Иван", "Марья", "Петр", "Антон", "Даша", "Борис", "Костя", "Игорь", "Анна", "Денис", "Андрей"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        находим список
        ListView lvMain = (ListView) findViewById(R.id.lvMain);

//        Создаем адаптер
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,R.layout.my_list_item,names);

//        Присваиваем адаптер списку
        lvMain.setAdapter(adapter);
    }
}
