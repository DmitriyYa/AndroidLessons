package com.example.p45expandablelist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    // названия компаний (групп)
    String[] groups = new String[]{"HTC", "Samsung", "LG"};

    // названия телефонов (элементов)
    String[] phonesHTC = new String[]{"Sensation", "Desire", "Wildfire", "Hero"};
    String[] phonesSams = new String[]{"Galaxy S II", "Galaxy Nexus", "Wave"};
    String[] phonesLG = new String[]{"Optimus", "Optimus Link", "Optimus Black", "Optimus One"};

    //коллекция для групп
    ArrayList<Map<String, String>> groupData;

    //коллекция для элементов одной группы
    ArrayList<Map<String, String>> childDataItem;

    //общая коллекция
    // в итоге получится childData = ArrayList<childDataItem>
    ArrayList<ArrayList<Map<String, String>>> childData;

    //список атрибутов группы или элемента
    Map<String, String> map;

    ExpandableListView elvMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //заполняем коллекцию групп из массива с названиями групп
        groupData = new ArrayList<Map<String, String>>();
        for (String group : groups) {
            //заполняем списки атрибутов для каждой группы
            map = new HashMap<String, String>();
            map.put("groupName", group);//имя компании
            groupData.add(map);
        }

        //Для связки атрибутов и TextView-элементов мы используем два массива:
        //  groupFrom – список имен атрибутов, которые будут считаны.
        //              В нашем случае – это groupName, который мы добавили к группе с помощью Map чуть выше в коде,
        //              когда собирали группы в groupData.
        //  groupTo – список ID View-элементов, в которые будут помещены считанные значения атрибутов.
        //  Наш используемый layout будет содержать TextView с ID = android.R.id.text1.

        //  Два этих массива сопоставляются по порядку элементов.
        //  В итоге, в layout-ресурсе группы найдется элемент с ID = android.R.id.text1
        //  и в него запишется текст из атрибута groupName.
        //  Тем самым мы получим отображение имени группы (компании) в списке.

        //список атрибутов групп для чтения
        String[] groupFrom = new String[]{"groupName"};
        //список id view-элементов, в которые будут помещены атрибуты групп
        int[] groupTO = new int[]{android.R.id.text1};

        //создаем коллекцию для коллекций элементов
        childData = new ArrayList<ArrayList<Map<String, String>>>();

        //создаем коллекцию элементов для первой группы
        childDataItem = new ArrayList<Map<String, String>>();
        //заполняем список атрибутов для каждого элемента
        for (String phone : phonesHTC) {
            map = new HashMap<String, String>();
            map.put("phoneName", phone);
            childDataItem.add(map);
        }
        //добавляем в коллекцию коллекций
        childData.add(childDataItem);

        //создаем коллекцию элементов для второй группы
        childDataItem = new ArrayList<Map<String, String>>();
        //заполняем список атрибутов для каждого элемента
        for (String phone : phonesSams) {
            map = new HashMap<String, String>();
            map.put("phoneName", phone);
            childDataItem.add(map);
        }
        //добавляем в коллекцию коллекций
        childData.add(childDataItem);

        //создаем коллекцию элементов для третьей группы
        childDataItem = new ArrayList<Map<String, String>>();
        //заполняем список атрибутов для каждого элемента
        for (String phone : phonesLG) {
            map = new HashMap<String, String>();
            map.put("phoneName", phone);
            childDataItem.add(map);
        }
        //добавляем в коллекцию коллекций
        childData.add(childDataItem);

        //список атрибутов элементов для чтения
        String[] childFrom = new String[]{"phoneName"};
        //список id view-элементов, в которые будут помещены атрибуты групп
        int[] childTo = new int[]{android.R.id.text1};

        //this – контекст
        //groupData – коллекция групп
        //android.R.layout.simple_expandable_list_item_1 – layout-ресурс, который будет использован для отображения
        //  группы в списке. Соответственно, запросто можно использовать свой layout-файл.
        //groupFrom – массив имен атрибутов групп
        //groupTo – массив ID TextView из layout для групп
        //childData – коллекция коллекций элементов по группам
        //android.R.layout.simple_list_item_1 - layout-ресурс, который будет использован для отображения элемента в
        //  списке. Можно использовать свой layout-файл
        //childFrom – массив имен атрибутов элементов
        //childTo - массив ID TextView из layout для элементов.
        SimpleExpandableListAdapter adapter = new SimpleExpandableListAdapter(
                this,
                groupData,
                android.R.layout.simple_expandable_list_item_1,//это TextView с отступом от левого края, чтобы осталось место для кнопки раскрытия/сворачивания списка.
                groupFrom,
                groupTO,
                childData,
                android.R.layout.simple_list_item_1,
                childFrom,
                childTo);
        elvMain = (ExpandableListView) findViewById(R.id.elvMain);
        elvMain.setAdapter(adapter);
    }
}
