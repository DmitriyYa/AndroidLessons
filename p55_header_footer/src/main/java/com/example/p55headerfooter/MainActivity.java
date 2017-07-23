package com.example.p55headerfooter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.HeaderViewListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
//
//    В этом уроке:
//    - используем Header и Footer в списках
//    - разбираемся, как и где используется HeaderViewListAdapter

//    Программист должен сам создать View и предоставить его списку в методы addHeader или addFooter.
//    У этих методов есть две реализации. Рассмотрим на примере Header.
//    1) addHeaderView (View v, Object data, boolean isSelectable)
//    v – View, которое отобразится, как пункт списка
//    data – объект, связанный с этим пунктом списка
//    isSelectable – можно ли будет кликать на пункт или выделять его
//    2) addHeaderView (View v)
//    Тут просто идет вызов первого метода с параметрами: addHeaderView(v, null, true);

    final String LOG_TAG = "myLogs";

    String[] data = {"one", "two", "three", "four", "five"};
    ListView lvMain;
    ArrayAdapter<String> adapter;

    View header1;
    View header2;

    View footer1;
    View footer2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvMain = (ListView) findViewById(R.id.lvMain);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data);

        // создаем Header и Footer
        header1 = createHeader("header 1");
        header2 = createHeader("header 2");
        footer1 = createFooter("footer 1");
        footer2 = createFooter("footer 2");

        fillList();
    }

    // формирование списка
    void fillList() {
        lvMain.addHeaderView(header1);
        lvMain.addHeaderView(header2, "some text for header 2", false);
        lvMain.addFooterView(footer1);
        lvMain.addFooterView(footer2, "some text for footer 2", false);
        lvMain.setAdapter(adapter);
    }

    // нажатие кнопки
    //Попробуем удалить HF. Метод onclick перепишем так:
    public void onclick(View v) {

        //Если да, то список оборачивает полученный адаптер в HeaderViewListAdapter,используя конструктор:
        // HeaderViewListAdapter (ArrayList<ListView.FixedViewInfo> headerViewInfos, ArrayList<ListView.FixedViewInfo> footerViewInfos, ListAdapter adapter),
        // где headerViewInfos и footerViewInfos – это ранее добавленные к списку HF,
        // а adapter – тот адаптер, который мы даем списку.
        // И теперь при работе списка будут использоваться методы адаптера HeaderViewListAdapter,
        // т.е. будут использоваться как HF, так и данные из адаптера, который присвоили списку.
        Object obj;

        //Сначала мы с помощью метода getAdapter получаем адаптер, который использует ListView.
        HeaderViewListAdapter hvlAdapter = (HeaderViewListAdapter) lvMain.getAdapter();

        //Т.к. мы добавляли HF к списку, то он использует адаптер HeaderViewListAdapter.
        // Попробуем получить данные по второму и пятому пункту этого адаптера,
        // вызвав getItem и выведем значение в лог.
        obj = hvlAdapter.getItem(1);
        Log.d(LOG_TAG, "hvlAdapter.getItem(1) = " + obj.toString());
        obj = hvlAdapter.getItem(4);
        Log.d(LOG_TAG, "hvlAdapter.getItem(4) = " + obj.toString());

        //Далее мы от HeaderViewListAdapter получаем его вложенный адаптер.
        // Это тот адаптер, который мы давали на вход списку в методе setAdapter.
        // Но т.к. были использованы HF, то список создал HeaderViewListAdapter в качестве основного адаптера
        // и отдал ему наш в качестве вложенного и теперь мы его получаем методом getWrappedAdapter.
        // Попробуем от нашего адаптера получить данные также по второму и пятому пункту и вывести в лог.
        ArrayAdapter<String> alAdapter = (ArrayAdapter<String>) hvlAdapter.getWrappedAdapter();
        obj = alAdapter.getItem(1);
        Log.d(LOG_TAG, "alAdapter.getItem(1) = " + obj.toString());
        obj = alAdapter.getItem(4);
        Log.d(LOG_TAG, "alAdapter.getItem(4) = " + obj.toString());
    }

    // создание Header
    View createHeader(String text) {
        View v = getLayoutInflater().inflate(R.layout.header, null);
        ((TextView) v.findViewById(R.id.tvText)).setText(text);
        return v;
    }

    // создание Footer
    View createFooter(String text) {
        View v = getLayoutInflater().inflate(R.layout.footer, null);
        ((TextView) v.findViewById(R.id.tvText)).setText(text);
        return v;
    }
}
