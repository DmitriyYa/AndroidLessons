package com.example.p49simpleadaptercustom1;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
//http://startandroid.ru/ru/uroki/vse-uroki-spiskom/108-urok-49-simpleadapter-metody-setviewtext-i-setviewimage.html

//В этом уроке:
//- используем методы SetViewText и SetViewImage

//Мы уже знаем, что SimpleAdapter умеет вставлять текст в TextView элементы и изображения в ImageView.
//Он использует для этого методы SetViewText и SetViewImage.
//Мы можем создать свой адаптер на основе SimpleAdapter и реализовать эти методы под наши цели.

//Эти методы предоставляют нам View и данные, а значит мы можем менять View в зависимости от данных.
//В качестве примера, сделаем список, отражающий динамику некоего показателя в разрезе дней.
//Если динамика положительная – будем разукрашивать элементы в зеленый цвет, если отрицательная – в красный.

public class MainActivity extends AppCompatActivity {

    // имена атрибутов для Map
    final String ATTRIBUTE_NAME_TEXT = "text";
    final String ATTRIBUTE_NAME_VALUE = "value";
    final String ATTRIBUTE_NAME_IMAGE = "image";

    // картинки для отображения динамики
    final int positive = android.R.drawable.arrow_up_float;
    final int negative = android.R.drawable.arrow_down_float;

    ListView lvSimple;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // массив данных
        int[] values = {8, 4, -3, 2, -5, 0, 3, -6, 1, -1};

        // упаковываем данные в понятную для адаптера структуру
        ArrayList<Map<String, Object>> data = new ArrayList<Map<String, Object>>(values.length);
        Map<String, Object> m;
        int img = 0;
        for (int i = 0; i < values.length; i++) {
            m = new HashMap<String, Object>();
            m.put(ATTRIBUTE_NAME_TEXT, "Day " + (i + 1));
            m.put(ATTRIBUTE_NAME_VALUE, values[i]);
            if (values[i] == 0) {
                img = 0;
            } else img = (values[i] > 0) ? positive : negative;
            m.put(ATTRIBUTE_NAME_IMAGE, img);
            data.add(m);
        }

        //массив имен атрибутов, из которых будут читаться данные
        String[] from = {ATTRIBUTE_NAME_TEXT, ATTRIBUTE_NAME_VALUE, ATTRIBUTE_NAME_IMAGE};

        //массив ID View- компонентов, в которые будут вставлять данные
        int[] to = {R.id.tvText, R.id.tvValue, R.id.ivImg};

        //создаем адаптер
        MySimpleAdapter adapter = new MySimpleAdapter(this, data, R.layout.item, from, to);

        //определяем список и присваиваем ему адаптер
        lvSimple = (ListView) findViewById(R.id.lvSimple);
        lvSimple.setAdapter(adapter);

    }

    class MySimpleAdapter extends SimpleAdapter {
        /**
         * Constructor
         *
         * @param context  The context where the View associated with this SimpleAdapter is running
         * @param data     A List of Maps. Each entry in the List corresponds to one row in the list. The
         *                 Maps contain the data for each row, and should include all the entries specified in
         *                 "from"
         * @param resource Resource identifier of a view layout that defines the views for this list
         *                 item. The layout file should include at least those named views defined in "to"
         * @param from     A list of column names that will be added to the Map associated with each
         *                 item.
         * @param to       The views that should display column in the "from" parameter. These should all be
         *                 TextViews. The first N views in this list are given the values of the first N columns
         */
        public MySimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
            super(context, data, resource, from, to);
        }

        @Override
        public void setViewImage(ImageView v, int value) {
            super.setViewImage(v, value);

//            разрисовываем ImageView
            if (value == negative) {
                v.setBackgroundColor(Color.RED);
            } else if (value == positive) {
                v.setBackgroundColor(Color.GREEN);
            }
        }

        @Override
        public void setViewText(TextView v, String text) {
            super.setViewText(v, text);

//            если нужен нам TexView то разрисовываем
            if (v.getId() == R.id.tvValue) {
                int i = Integer.parseInt(text);
                if (i < 0) {
                    v.setTextColor(Color.RED);
                } else if (i > 0) {
                    v.setTextColor(Color.GREEN);
                }
            }


        }
    }
}
