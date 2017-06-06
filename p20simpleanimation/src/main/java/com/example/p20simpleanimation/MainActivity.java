package com.example.p20simpleanimation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // константы для ID пунктов меню
    final static int MENU_ALPHA_ID = 1;
    final static int MENU_SCALE_ID = 2;
    final static int MENU_TRANSLATE_ID = 3;
    final static int MENU_ROTATE_ID = 4;
    final static int MENU_COMBO_ID = 5;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        textView = (TextView) findViewById(R.id.tv);

// регистрируем контекстное меню для компонента tv
        registerForContextMenu(textView);
    }

    //    Создаем контекстное меню
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        switch (v.getId()) {
            case R.id.tv:
                // добавляем пункты
                menu.add(0, MENU_ALPHA_ID, 0, "alpha");
                menu.add(0, MENU_SCALE_ID, 0, "scale");
                menu.add(0, MENU_TRANSLATE_ID, 0, "translate");
                menu.add(0, MENU_ROTATE_ID, 0, "rotate");
                menu.add(0, MENU_COMBO_ID, 0, "combo");
                break;
        }
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    //    Обработка выбранных пунктов
    @Override
    public boolean onContextItemSelected(MenuItem item) {

//Анимация читается из xml-файла методом AnimationUtils.loadAnimation,
// на выходе получается объект типа Animation.
// Его используем в методе startAnimation, который запускает анимацию.
        Animation animation = null;

// определяем какой пункт был нажат
        switch (item.getItemId()) {

            case MENU_ALPHA_ID:
//создаем объект анимации из файла anim/myalpha
                animation = AnimationUtils.loadAnimation(this, R.anim.myalpha);
                break;
            case MENU_COMBO_ID:
                animation=AnimationUtils.loadAnimation(this,R.anim.mycombo);
                break;
            case MENU_SCALE_ID:
                animation=AnimationUtils.loadAnimation(this,R.anim.myscale);
                break;
            case MENU_TRANSLATE_ID:
                animation=AnimationUtils.loadAnimation(this,R.anim.mytrans);
                break;
            case MENU_ROTATE_ID:
                animation=AnimationUtils.loadAnimation(this,R.anim.myrotate);
                break;
        }
        // запускаем анимацию для компонента tv
        textView.startAnimation(animation);

        return super.onContextItemSelected(item);
    }
}
