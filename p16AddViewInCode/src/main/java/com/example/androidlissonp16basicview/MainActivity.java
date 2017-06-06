package com.example.androidlissonp16basicview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    RadioGroup radioGroup;
    EditText editText;
    Button btnCreate;
    Button btnCleare;
    LinearLayout llMain;

    int wrapContent = LinearLayout.LayoutParams.WRAP_CONTENT;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//рисуем экран программно, а не через layout-файл
////        за основу возьмем LineryLayout
//        LinearLayout ll = new LinearLayout(this);
//
//        ll.setOrientation(LinearLayout.VERTICAL);
//
//        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//
////        добавим элемент
//        ViewGroup.LayoutParams lpV=new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
//
//        TextView tv=new TextView(this);
//        tv.setText("привет!");
//        tv.setLayoutParams(lpV);
//        ll.addView(tv);
//
//        Button b=new Button(this);
//        b.setText("Кнопка");
//        ll.addView(b,lpV);
//
//        setContentView(ll, lp);

//        Добавляем к текущему LL, программно элементы.

        llMain = (LinearLayout) findViewById(R.id.llMain);
        radioGroup = (RadioGroup) findViewById(R.id.rgGravity);
        editText = (EditText) findViewById(R.id.etName);

        btnCreate = (Button) findViewById(R.id.btnCreate);
        btnCreate.setOnClickListener(this);

        btnCleare = (Button) findViewById(R.id.btnClear);
        btnCleare.setOnClickListener(this);

    }

//    обрабатываем нажатие кнопок
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCreate:

                // Создание LayoutParams c шириной и высотой по содержимому
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(wrapContent, wrapContent);

                // переменная для хранения значения выравнивания
                // по умолчанию пусть будет LEFT
                int btnGravity = Gravity.LEFT;

                // определяем, какой RadioButton "чекнут" и
                // соответственно заполняем btnGravity
                switch (radioGroup.getCheckedRadioButtonId()) {
                    case R.id.rbLeft:
                        btnGravity = Gravity.LEFT;
                        break;
                    case R.id.rbRight:
                        btnGravity = Gravity.RIGHT;
                        break;
                    case R.id.rbCenter:
                        btnGravity = Gravity.CENTER_HORIZONTAL;
                        break;
                }

                // переносим полученное значение выравнивания в LayoutParams
                layoutParams.gravity = btnGravity;

                Button buttonNew = new Button(this);
                buttonNew.setText(editText.getText().toString());
                llMain.addView(buttonNew, layoutParams);
                break;

            case R.id.btnClear:
                llMain.removeAllViews();
                Toast.makeText(this,"Удалены все элементы",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
