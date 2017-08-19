package com.example.p81_handler_simple_message;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

//В этом уроке:
//- посылаем простейшее сообщение для Handler

//Напишем простое приложение-клиент.
//Оно, как-будто, будет подключаться к серверу, выполнять какую-то работу и отключаться.
//На экране мы будем наблюдать, как меняется статус подключения и как крутится ProgressBar при подключении.

//При сменах состояния подключения мы будем отправлять сообщение для Handler.
//А в атрибут what будем класть текущий статус.
// Handler при обработке сообщения прочтет из него what и выполнит какие-либо действия.

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
