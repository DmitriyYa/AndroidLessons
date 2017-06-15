package com.example.p41layoutinflaterlist;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

//http://startandroid.ru/ru/uroki/vse-uroki-spiskom/81-urok-41-ispolzuem-layoutinflater-dlja-sozdanija-spiska.html

//В этом уроке:
//- делаем свой вариант списка


//Приложение будет параллельно перебирать три массива данных,
// создавать для каждой тройки View-элемент из layout-файла item.xml,
// заполнять его данными и добавлять в вертикальный LinearLayout в main.xml.

public class MainActivity extends AppCompatActivity {

    String[] name = {"Иван", "Марья", "Петр", "Антон", "Даша", "Борис", "Костя", "Игорь"};
    String[] position = {"Программер", "Бухгалтер", "Программер", "Программер", "Бухгалтер", "Директор", "Программер", "Охранник"};
    int salary[] = {13000, 10000, 13000, 13000, 10000, 15000, 13000, 8000};

    int[] colors = new int[2];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        colors[0] = Color.parseColor("#559966CC");
        colors[1] = Color.parseColor("#55336699");

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linLayout);

        LayoutInflater layoutInflater = getLayoutInflater();

        //В каждой итерации создаем View-элемент item из layout-файла item.xml.
        // В нашем случае item - это FrameLayout, который содержит три TextView.
        // Мы их находим в созданном item и заполняем данными из массивов.
        for (int i = 0; i < name.length; i++) {
            Log.d("myLogs", "i = " + i);

            // третий параметр inflate мы указали false.
            // Т.е. мы не стали сразу добавлять создаваемый View-элемент к linLayout,
            // а делаем это в конце кода методом addView. Этому есть объяснение.
            // Если бы мы указали true - то метод добавил бы item к linLayout и вернул бы нам linLayout,
            // общий для всех пунктов списка.
            // Через linLayout заполнять TextView необходимым нам текстом было бы затруднительно.
            // Поэтому мы получаем пункт item (FrameLayout), заполняем его TextView данными
            // и только потом помещаем к остальным пунктам в linLayout методом addView.
            View item = layoutInflater.inflate(R.layout.item, linearLayout, false);

            TextView tvName = (TextView) item.findViewById(R.id.tvName);
            tvName.setText(name[i]);

            TextView tvPosition = (TextView) item.findViewById(R.id.tvPosition);
            tvPosition.setText("Должность: " + position[i]);

            TextView tvSalary = (TextView) item.findViewById(R.id.tvSalary);
            tvSalary.setText("Оклад: " + salary[i]);

            item.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
            item.setBackgroundColor(colors[i%2]);
            linearLayout.addView(item);
        }
    }
}
