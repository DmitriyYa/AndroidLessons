package com.example.p44simplelistevents;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

//http://startandroid.ru/ru/uroki/vse-uroki-spiskom/85-urok-44-sobytija-v-listview.html

//В этом уроке:
//- рассматриваем события ListView: нажатие - onItemClick, выделение - onItemSelect, прокрутка - onScroll

public class MainActivity extends AppCompatActivity {

    public static final String LOG_TAG = "myLogs";

    ListView lvMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvMain = (ListView) findViewById(R.id.lvMain);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.names,
                android.R.layout.simple_list_item_1);
        lvMain.setAdapter(adapter);

        //OnItemClickListener – обрабатывает нажатие на пункт списка
        lvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            //parent – View-родитель для нажатого пункта, в нашем случае - ListView
            //view – это нажатый пункт, в нашем случае – TextView из android.R.layout.simple_list_item_1
            //position – порядковый номер пункта в списке
            //id – идентификатор элемента,
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(LOG_TAG, " itemClick: position = " + position + ", id = " + id);
            }
        });

        //OnItemSelectedListener – обрабатывает выделение пунктов списка (не check, как на прошлом уроке)
        lvMain.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d(LOG_TAG, "itemSelect: position" + position + ", id " + id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.d(LOG_TAG, "itemSelected: nothing");
            }
        });

        //OnScrollListener – обрабатывает прокрутку списка.
        lvMain.setOnScrollListener(new AbsListView.OnScrollListener() {

            //обработка состояний прокрутки
            //view – это прокручиваемый элемент, т.е. ListView
            //scrollState – состояние списка. Может принимать три значения:
            //  SCROLL_STATE_IDLE = 0, список закончил прокрутку
            //  SCROLL_STATE_TOUCH_SCROLL = 1, список начал прокрутку
            //  SCROLL_STATE_FLING = 2, список «катнули»,
            //  т.е. при прокрутке отпустили палец и прокрутка дальше идет «по инерции»
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                Log.d(LOG_TAG, "scrollState = " + scrollState);
            }

            //обработка прокрутки
            //view – прокручиваемый элемент
            //firstVisibleItem – первый видимый на экране пункт списка
            //visibleItemCount – сколько пунктов видно на экране
            //totalItemCount – сколько всего пунктов в списке
            //Причем для параметров firstVisibleItem и visibleItemCount пункт считается видимым на экране
            // даже если он виден не полностью.
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                Log.d(LOG_TAG,
                        "scroll : firstVisibleItem = " + firstVisibleItem
                        + ", visibleItemCount = " + visibleItemCount
                        + ", totalItemCount = " + totalItemCount);
            }
        });
    }
}
