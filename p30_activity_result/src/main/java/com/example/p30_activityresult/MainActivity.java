package com.example.p30_activityresult;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

//    - разбираемся, зачем нужны requestCode и resultCode в onActivityResult
//http://startandroid.ru/ru/uroki/vse-uroki-spiskom/69-urok-30-podrobnee-pro-onactivityresult-zachem-nuzhny-requestcode-i-resultcode.html

    Button btnColor;
    Button btnAllingment;
    TextView textView;

    final int REQUEST_CODE_COLOR = 1;
    final int REQUEST_CODE_ALIGNMENT = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnColor = (Button) findViewById(R.id.btnColor);
        btnColor.setOnClickListener(this);

        btnAllingment = (Button) findViewById(R.id.btnAlign);
        btnAllingment.setOnClickListener(this);

        textView = (TextView) findViewById(R.id.tvText);

    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.btnColor:
                intent = new Intent(this, ColorActivity.class);
                startActivityForResult(intent, REQUEST_CODE_COLOR);
                break;
            case R.id.btnAlign:
                intent = new Intent(this, AllignentActivity.class);
                startActivityForResult(intent, REQUEST_CODE_ALIGNMENT);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // запишем в лог значения requestCode и resultCode
        Log.d("myLogs", "request code = " + requestCode + ", result code = " + resultCode);

//        если пришло ок
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_COLOR:
                    int color = data.getIntExtra("color", Color.WHITE);
                    textView.setTextColor(color);
                    break;
                case REQUEST_CODE_ALIGNMENT:
                    int alignment = data.getIntExtra("alignment", Gravity.LEFT);
                    textView.setGravity(alignment);
                    break;
            }
        }
// если вернулось не ОК
        else {
            Toast.makeText(this, "Неверный результат", Toast.LENGTH_SHORT).show();
        }
    }
    /**
     * Итак, подведем итог.

     requestCode – это в некотором роде ID запроса.
     Задается в методе startActivityForResult, и проверяется потом в onActivityResult, чтобы точно знать, на какой вызов пришел ответ.

     resultCode – статус вызова.
     Задается в методе setResult, и проверяется в onActivityResult, чтобы понять насколько успешно прошел вызов.
     Если при вызове что-то пошло не так, то вернется системная константа RESULT_CANCELED.
     */
}

