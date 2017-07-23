package com.example.p60alertdialogsimple;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

//В этом уроке:
//- создаем AlertDialog
//- настраиваем заголовок, сообщение, картинку и кнопки
public class MainActivity extends AppCompatActivity {

//Начнем знакомство с AlertDialog. Этот диалог используется,
// если вы хотите сообщить о чем-то пользователю или попросить его сделать выбор типа Да/Нет/Отмена.

//Напишем приложение, которое при закрытии будет вызывать диалог о сохранении данных,
// аналогичный диалогу из программ MS Office . Если мы ответим Да, то данные сохранятся,
// если Нет – то не сохранятся, если Отмена – приложение не закроется.

    final int DIALOG_EXIT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onclick(View v) {
        // вызываем диалог
        showDialog(DIALOG_EXIT);
    }

    //Создаем диалог
    protected Dialog onCreateDialog(int id) {
        if (id == DIALOG_EXIT) {
            //для этого используем AlertDialog.Builder
            AlertDialog.Builder adb = new AlertDialog.Builder(this);
            // заголовок
            adb.setTitle(R.string.exit);
            // сообщение
            adb.setMessage(R.string.save_data);
            // иконка
            adb.setIcon(android.R.drawable.ic_dialog_info);
            // кнопка положительного ответа
            adb.setPositiveButton(R.string.yes, myClickListener);
            // кнопка отрицательного ответа
            adb.setNegativeButton(R.string.no, myClickListener);
            // кнопка нейтрального ответа
            adb.setNeutralButton(R.string.cancel, myClickListener);
            //если хотите, чтобы вызванный диалог не закрывался по нажатию кнопки Назад, то используйте метод setCancelable:
            //adb.setCancelable(false);
            // создаем диалог
            return adb.create();
        }
        return super.onCreateDialog(id);
    }

    //Обработчик кнопок myClickListener реализует интерфейс DialogInterface.OnClickListener
    //и в нашем случае является общим для всех кнопок. В нем мы проверяем, какая кнопка была нажата:
    DialogInterface.OnClickListener myClickListener = new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                // положительная кнопка
                case Dialog.BUTTON_POSITIVE:
                    saveData();
                    finish();
                    break;
                // негативная кнопка
                case Dialog.BUTTON_NEGATIVE:
                    finish();
                    break;
                // нейтральная кнопка
                case Dialog.BUTTON_NEUTRAL:
                    break;
            }
        }
    };

    void saveData() {
        Toast.makeText(this, R.string.saved, Toast.LENGTH_SHORT).show();
    }

//Чтобы диалог вызывался не только по кнопке выход, но и при нажатии на кнопку Назад в приложении,
// добавьте вызов диалога в реализацию метода onBackPressed

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        // вызываем диалог
        showDialog(DIALOG_EXIT);
    }
}
