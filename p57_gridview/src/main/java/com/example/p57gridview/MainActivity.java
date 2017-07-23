package com.example.p57gridview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.GridView;

public class MainActivity extends AppCompatActivity {
//В этом уроке:
//- используем GridView

//GridView – еще один из компонентов, использующих адаптеры.
//Он выводит элементы в виде сетки/матрицы/таблицы, нужное подчеркнуть )

    String[] data = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k"};

    GridView gvMain;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //В качестве layout для адаптера используем созданный item.xml,
        // а tvText – это элемент, в который адаптер будет вставлять текст.
        adapter = new ArrayAdapter<String>(this, R.layout.item, R.id.tvText, data);
        gvMain = (GridView) findViewById(R.id.gvMain);
        gvMain.setAdapter(adapter);
        adjustGridView();
    }

    //будем кодить настройки Grid-а.
    private void adjustGridView() {
        //numColumns – кол-во столбцов в сетке. Если его не задавать, то столбец будет по умолчанию один.

        //Это свойство также может иметь значение AUTO_FIT.
        // В этом случае проверяется значение поля атрибута columnWidth (ширина столбца).
        //- если ширина столбца явно указана, то кол-во столбцов рассчитывается исходя из ширины, доступной GridView, и ширины столбцов.//
        //- иначе, кол-во столбцов считается равным 2
        gvMain.setNumColumns(GridView.AUTO_FIT);

        //Теперь укажем явно ширину столбцов, пусть будет 50.
        gvMain.setColumnWidth(80);

        //horizontalSpacing, verticalSpacing
        //Это горизонтальный и вертикальный отступы между ячейками. Пусть будет 5.
        gvMain.setVerticalSpacing(5);
        gvMain.setHorizontalSpacing(5);

        //stretchMode
        //Этот параметр определяет, как будет использовано свободное пространство, если оно есть.
        //Используется в случае, когда вы указываете ширину столбца и кол-во ставите в режим AUTO_FIT.
        //stretchMode может принимать 4 значения:
        //NO_STRETCH – свободное пространство не используется
        //STRETCH_COLUMN_WIDTH – свободное пространство используется столбцами, это режим по умолчанию.
        //                       Столбцы растянуты по ширине. Она уже может не соответствовать той,
        //                       что указана в setColumnWidth.
        //STRETCH_SPACING – свободное пространство равномерно распределяется между столбцами
                            //Ширина столбцов неизменна. Увеличены интервалы между ними.
        //STRETCH_SPACING_UNIFORM – свободное пространство равномерно распределяется не только между столбцами,
        // но и справа и слева
        gvMain.setStretchMode(GridView.STRETCH_SPACING);
    }
}
