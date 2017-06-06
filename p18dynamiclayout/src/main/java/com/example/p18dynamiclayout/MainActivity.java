package com.example.p18dynamiclayout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {
    SeekBar seekBar;
    Button button1;
    Button button2;

    LinearLayout.LayoutParams layoutParams1;
    LinearLayout.LayoutParams layoutParams2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seekBar = (SeekBar) findViewById(R.id.seekBar);
//надо обработчик (Activity) присвоить View-компоненту, события которого необходимо обрабатывать:
        seekBar.setOnSeekBarChangeListener(this);

        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
/**
 * Мы используем метод getLayoutParams для получения LayoutParams компонента.
 * Но этот метод возвращает базовый ViewGroup.LayoutParams, а нам нужен LinearLayout.LayoutParams,
 * поэтому делаем преобразование. В результате - lParams1 и lParams2 теперь являются LayoutParams
 * для компонентов btn1 и btn2. Т.е. работая, например, с lParams1 мы влияем на btn1. Сейчас
 */
        layoutParams1 = (LinearLayout.LayoutParams) button1.getLayoutParams();
        layoutParams2 = (LinearLayout.LayoutParams) button2.getLayoutParams();

    }

    //    срабатывает все время, пока значение меняется
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        int leftValue = progress;
        int rightValue = seekBar.getMax() - progress;
// настраиваем вес
        layoutParams1.weight = leftValue;
        layoutParams2.weight = rightValue;
        button1.setText(String.valueOf(leftValue));
        button2.setText(String.valueOf(rightValue));


    }

    //    срабатывает, когда начинаем тащить ползунок
    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    //    срабатывает, когда отпускаем ползунок
    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
