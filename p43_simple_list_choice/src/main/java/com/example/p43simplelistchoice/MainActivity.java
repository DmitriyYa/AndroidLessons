package com.example.p43simplelistchoice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

//http://startandroid.ru/ru/uroki/vse-uroki-spiskom/83-urok-43-odinochnyj-i-mnozhestvennyj-vybor-v-list.html

//В этом уроке:
//- используем список ListView для одиночного и множественного выбора элементов

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String LOG_TAG = "myLogs";

    ListView lvMain;
    String[] name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvMain = (ListView) findViewById(R.id.lvMain);

        //устанавливаем режим выбора пунктов списка
        //Мы устанавливаем для списка режим выбора - CHOICE_MODE_SINGLE.
        // Это значит, что список будет хранить позицию последнего нажатого пункта
        // и мы всегда можем запросить у него эту информацию.
//        lvMain.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        lvMain.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        //Создаем адаптер, используе массив из файла ресурса
        //Далее мы создаем адаптер, но не через обычный конструктор, а с использованием метода createFromResource.
        // Параметры на вход почти те же, что и в обычном конструкторе,
        // только вместо массива данных, мы указываем массив строк в ресурсах, который мы создали чуть раньше.
        // В качестве layout-ресурса для пунктов используем системный simple_list_item_single_choice.
        // Он как раз заточен под такое использование.
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.names,
//                android.R.layout.simple_list_item_single_choice);
                android.R.layout.simple_list_item_multiple_choice);
        lvMain.setAdapter(adapter);

        Button btnChecked = (Button) findViewById(R.id.btnChecked);
        btnChecked.setOnClickListener(this);

        //получаем массив из фаила ресурсов
        name=getResources().getStringArray(R.array.names);
    }

    @Override
    public void onClick(View v) {

        //пишем в лог выделенный элемент
        //В качестве индекса используем позицию пункта в списке.
        // Последовательность элементов в массиве и в списке совпадают.
//        Log.d(LOG_TAG,"checked : "+name[lvMain.getCheckedItemPosition()]);


        //пишем в лог выделенные элементы
        Log.d(LOG_TAG, " checked : ");

        //Мы получаем позиции выделенных элементов в виде объекта SparseBooleanArray.
        //Он представляет собой Map(int, boolean).
        //Ключ (int) – это позиция элемента, а значение (boolean) – это выделен пункт списка или нет.
        //Причем SparseBooleanArray хранит инфу не о всех пунктах,
        //а только о тех, с которыми проводили действие (выделяли и снимали выделение).
        //Мы перебираем его содержимое, получаем позицию пункта и, если пункт выделен,
        //то выводим в лог имя из массива, соответствующее позиции пункта.
        SparseBooleanArray sbArray=lvMain.getCheckedItemPositions();
        for (int i=0; i<sbArray.size(); i++){
            int key = sbArray.keyAt(i);
            if (sbArray.get(key)){
                Log.d(LOG_TAG, name[key]);
            }
        }
    }
}
