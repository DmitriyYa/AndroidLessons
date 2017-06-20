package com.example.p48simpleadapter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
//http://startandroid.ru/ru/uroki/vse-uroki-spiskom/107-urok-48-ispolzuem-simpleadapter.html

//В этом уроке:
//- рассмотрим пример использования SimpleAdapter

//SimpleAdapter работает с данными такого вида List<? extends Map<String, ?>> - т.е.
// коллекция Map-объектов (или -наследников).
// Если смотреть с точки зрения списка, то каждый Map содержит данные для соответствующего пункта списка.
// Чтобы адаптер знал, какие данные в какие View-компоненты каждого пункта списка ему вставлять,
// мы указываем два массива: String[] from и int[] to.
// В from перечисляем ключи из Map, а в to – ID View-компонентов.
// Адаптер перебирает View из массива to и сопоставляет им значения из Map по ключам из from.

public class MainActivity extends AppCompatActivity {

    // имена атрибутов для Map
    final String ATTRIBUTE_NAME_TEXT = "text";
    final String ATTRIBUTE_NAME_CHECKED = "checked";
    final String ATTRIBUTE_NAME_IMAGE = "image";

    ListView lvSimple;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // массивы данных
        String[] texts = { "sometext 1", "sometext 2", "sometext 3", "sometext 4", "sometext 5" };
        boolean[] checked = { true, false, false, true, false };
        int img = R.mipmap.ic_launcher;

        // упаковываем данные в понятную для адаптера структуру
        ArrayList<Map<String, Object>> data = new ArrayList<Map<String, Object>>(texts.length);
        Map<String, Object> m;
        for (int i = 0; i < texts.length; i++) {
            m = new HashMap<String, Object>();
            m.put(ATTRIBUTE_NAME_TEXT, texts[i]);
            m.put(ATTRIBUTE_NAME_CHECKED, checked[i]);
            m.put(ATTRIBUTE_NAME_IMAGE, img);
            data.add(m);
        }

        // массив имен атрибутов, из которых будут читаться данные
        String[] from = { ATTRIBUTE_NAME_TEXT, ATTRIBUTE_NAME_CHECKED, ATTRIBUTE_NAME_IMAGE };

        // массив ID View-компонентов, в которые будут вставлять данные
        int[] to = { R.id.tvText, R.id.cbChecked, R.id.ivImg };

        // создаем адаптер
        SimpleAdapter sAdapter = new SimpleAdapter(this, data, R.layout.item, from, to);

        // определяем список и присваиваем ему адаптер
        lvSimple = (ListView) findViewById(R.id.lvSimple);
        lvSimple.setAdapter(sAdapter);
    }
}
