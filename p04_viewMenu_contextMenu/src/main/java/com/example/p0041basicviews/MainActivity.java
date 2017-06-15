package com.example.p0041basicviews;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.p0041basicviews.R.id.group1;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public TextView textViewSize;
    public TextView textViewColore;

    public Button button1;
    public Button button2;
    public CheckBox checkBox;

    final int MENU_COLOR_RED=1;
    final int MENU_COLOR_GREEN=2;
    final int MENU_COLOR_BLUE=3;

    final int MENU_SIZE_22=4;
    final int MENU_SIZE_26=5;
    final int MENU_SIZE_30=6;

    private static final String TAG = "myLogs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myscreen);

        Log.d(TAG, "// найдем View-элементы");
        textViewSize = (TextView) findViewById(R.id.textView);
        textViewColore = (TextView) findViewById(R.id.textView3);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        checkBox= (CheckBox) findViewById(R.id.checkBox2);

        Log.d(TAG, "//обработаем нажатие");
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);

//        для tvColor и tvSize необходимо создавать контекстное меню
        registerForContextMenu(textViewColore);
        registerForContextMenu(textViewSize);

    }

//обработка нажатия кнопок
    @Override
    public void onClick(View v) {
        Log.d(TAG, "//по id определяем кнопку, вызвавшую обработчик");
        switch (v.getId()) {

            case R.id.button1:
                Log.d(TAG, "//кнопка ОК");
                button1.setBackgroundResource(R.color.colorTeal200);
                textViewSize.setText("Нажата кнопка ОК");
                Toast.makeText(this,"Надата кнопка Ок",Toast.LENGTH_SHORT).show();
                break;

            case R.id.button2:
                Log.d(TAG, "//кнопка Отмена");
                button2.setBackgroundResource(R.color.colorTeal200);
                textViewSize.setText("Нажата кнопка Отмена");
                Toast.makeText(this,"Нажата кнопка Отмена",Toast.LENGTH_SHORT).show();
                break;
        }
    }

//    вызывается только при первом показе меню.
// Создает меню и более не используется. Здесь мы добавляем к меню пункты.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        С помощью метода getMenuInflater мы получаем MenuInflater и вызываем его метод inflate.
// На вход передаем наш файл mymenu.xml из папки res/menu и объект menu.
// MenuInflater берет объект menu и наполняет его пунктами согласно файлу mymenu.xml.
       getMenuInflater().inflate(R.menu.mymenu, menu);

        return super.onCreateOptionsMenu(menu);
    }

//    вызывается каждый раз перед отображением меню.
// Здесь мы вносим изменения в уже созданное меню, если это необходимо
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

//        setGroupVisible - позволяет скрывать\отображать пункты меню.
        menu.setGroupVisible(group1,checkBox.isChecked());
        return super.onPrepareOptionsMenu(menu);
    }

//    вызывается при нажатии пункта меню.
// Здесь мы определяем какой пункт меню был нажат.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        StringBuilder sb=new StringBuilder();

        sb.append("Item Menu");
        sb.append("\r\n groupId: "+String.valueOf(item.getGroupId()));
        sb.append("\r\n itemId: "+String.valueOf(item.getItemId()));
        sb.append("\r\n order: "+String.valueOf(item.getOrder()));
        sb.append("\r\n title: "+String.valueOf(item.getTitle()));
        textViewSize.setText(sb.toString());

        return super.onOptionsItemSelected(item);
    }

//    Создание контекстного меню
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        switch (v.getId()){
            case R.id.textView:
                menu.add(0,MENU_SIZE_22,0,"22");
                menu.add(0,MENU_SIZE_26,0,"26");
                menu.add(0,MENU_SIZE_30,0,"30");
                break;
            case R.id.textView3:
                menu.add(0,MENU_COLOR_BLUE,0,"Blue");
                menu.add(0,MENU_COLOR_RED,0,"Red");
                menu.add(0,MENU_COLOR_GREEN,0,"Green");
                break;
        }
    }

//    В этом методе мы определяем по ID, какой пункт меню был нажат.
// И выполняем соответствующие действия: меняем цвет текста для tvColor или размер шрифта для tvSize.
// Сохраняем, запускаем и проверяем, что контекстные меню теперь реагируют на нажатия и делают то,
// что от них требуется.
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case MENU_COLOR_BLUE:
                textViewColore.setTextColor(Color.BLUE);
                textViewColore.setText("Text color = blue");
                break;
            case MENU_COLOR_GREEN:
                textViewColore.setTextColor(Color.GREEN);
                textViewColore.setText("Text color = green");
                break;
            case MENU_COLOR_RED:
                textViewColore.setTextColor(Color.RED);
                textViewColore.setText("Text color = red");
                break;

            case MENU_SIZE_22:
                textViewSize.setTextSize(22);
                textViewSize.setText("TExt size = 22");
                break;
            case MENU_SIZE_26:
                textViewSize.setTextSize(26);
                textViewSize.setText("TExt size = 26");
                break;
            case MENU_SIZE_30:
                textViewSize.setTextSize(30);
                textViewSize.setText("TExt size = 30");
                break;

        }
        return super.onContextItemSelected(item);
    }
}
