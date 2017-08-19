package com.example.p80_handler;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

//В этом уроке:
//- разбираемся, что такое Handler и зачем он нужен

//В этом уроке сделаем небольшое приложение.
// Оно будет эмулировать какое-либо долгое действие, например закачку файлов и в TextView выводить кол-во закачанных файлов.
// С помощью этого примера мы увидим, зачем может быть нужен Handler.

public class MainActivity extends AppCompatActivity {

    final String LOG_TAG = "myLogs";

    Handler h;
    TextView tvInfo;
    Button btnStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvInfo = (TextView) findViewById(R.id.tvInfo);
        btnStart = (Button) findViewById(R.id.btnStart);

        h = new Handler() {
            //Здесь мы создаем Handler и в нем реализуем метод обработки сообщений handleMessage.
            //Мы извлекаем из сообщения атрибут what – это кол-во закачанных файлов.
            //Если оно равно 10, т.е. все файлы закачаны, мы активируем кнопку Start. (кол-во закачанных файлов мы сами кладем в сообщение - сейчас увидите, как)
            public void handleMessage(android.os.Message msg) {
                // обновляем TextView
                tvInfo.setText("Закачано файлов: " + msg.what);
                if (msg.what == 10) btnStart.setEnabled(true);
            };
        };
    }

    public void onclick(View v) {
        switch (v.getId()) {
            case R.id.btnStart:
//                for (int i = 1; i <= 10; i++) {
//                    // долгий процесс
//                    downloadFile();
//                    // обновляем TextView
//                    tvInfo.setText("Закачано файлов: " + i);
//                    // пишем лог
//                    Log.d(LOG_TAG, "Закачано файлов: " + i);
//                }
//                break;
                btnStart.setEnabled(false);
                Thread t = new Thread(new Runnable() {
                    public void run() {
                        for (int i = 1; i <= 10; i++) {
                            // долгий процесс
                            downloadFile();
                            //А в процессе закачки, после каждого закачанного файла, отправляем (sendEmptyMessage) для Handler сообщение с кол-вом уже закачанных файлов.
                            // Handler это сообщение примет, извлечет из него кол-во файлов и обновит TextView.
                            h.sendEmptyMessage(i);
                            // пишем лог
                            Log.d(LOG_TAG, "i = " + i);
                        }
                    }
                });
                t.start();
                break;

            case R.id.btnTest:
                Log.d(LOG_TAG, "test");
                break;

            default:
                break;
        }
    }

    private void downloadFile() {
        // пауза - 1 секунда
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
