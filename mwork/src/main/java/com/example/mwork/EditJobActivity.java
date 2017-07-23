package com.example.mwork;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

import static android.R.attr.data;

public class EditJobActivity extends AppCompatActivity implements View.OnClickListener {
    EditText etShortDesc;
    EditText etLongDesc;
    TextView tvTime;
    TextView tvDate;
    Button btnUpdate;
    Button btnCancel;
    Button btnSetTimeEdit;
    Button btnSetDateEdit;
    Spinner spEdit;

    String id;

    Intent intent;
    DB db;
    SQLiteDatabase mDB;

    Calendar dateAndTime = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_job);

        etShortDesc = (EditText) findViewById(R.id.etShotDescriptionEdit);
        etLongDesc = (EditText) findViewById(R.id.etLongDescriptionEdit);

        tvTime = (TextView) findViewById(R.id.tvTimeEdit);
        tvDate = (TextView) findViewById(R.id.tvDateEdit);

        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(this);

        btnCancel = (Button) findViewById(R.id.btnCancelEdit);
        btnCancel.setOnClickListener(this);

        btnSetTimeEdit = (Button) findViewById(R.id.btnSetTimeEdit);
        btnSetTimeEdit.setOnClickListener(this);

        btnSetDateEdit = (Button) findViewById(R.id.btnSetDateEdit);
        btnSetDateEdit.setOnClickListener(this);

        spEdit = (Spinner) findViewById(R.id.spinnerEdit);

        //извлекаем данные из интент и вставляем в поля
        intent = getIntent();
        etShortDesc.setText(intent.getStringExtra(DB.COLUMN_SHOT_DESC));
        etLongDesc.setText(intent.getStringExtra(DB.COLUMN_LONG_DESC));
        tvTime.setText(intent.getStringExtra(DB.COLUMN_TIME));
        tvDate.setText(intent.getStringExtra(DB.COLUMN_DATE));

        // адаптер
        //Создаем адаптер, используем simple_spinner_item в качестве layout для отображения Spinner на экране
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, CreateJobActivity.getData());

        //А методом setDropDownViewResource указываем какой layout использовать для прорисовки пунктов выпадающего списка.
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spEdit = (Spinner) findViewById(R.id.spinnerEdit);
        spEdit.setAdapter(adapter);

        //ищем позицию записи в SPINNER
        String[] spiner = CreateJobActivity.getData();
        int idSpiner = 0;
        for (int i = 0; i < spiner.length; i++) {
            if (spiner[i].equals(intent.getStringExtra(DB.COLUMN_STATUS))) {
                idSpiner = i;
            }
        }
        // выделяем элемент
        spEdit.setSelection(idSpiner);

        //получаем id записи
        id = intent.getStringExtra(DB.COLUMN_ID);

        db = new DB(this);
        mDB = db.open();
    }

    @Override
    public void onClick(View v) {
        //создаем объект для данных
        ContentValues contentValues = new ContentValues();

        // получаем данные из полей ввода

        String shotDesc = etShortDesc.getText().toString();
        String longDesc = etLongDesc.getText().toString();
        String time = tvTime.getText().toString();
        String date = tvDate.getText().toString();
        String spinner = spEdit.getSelectedItem().toString();

        switch (v.getId()) {
            case R.id.btnUpdate:
                //проверяем установлена ли дата и время выполнения задачи
                if (shotDesc.equals("")) {
                    Toast.makeText(this, "Заполните краткое описание задачи", Toast.LENGTH_SHORT).show();
                    break;
                }
                if (time.equals("")) {
                    Toast.makeText(this, "Заполните время выполнения задачи", Toast.LENGTH_SHORT).show();
                    break;
                }
                if (date.equals("")) {
                    Toast.makeText(this, "Заполните дату выполнения задачи", Toast.LENGTH_SHORT).show();
                    break;
                }

                db.updRec(id, shotDesc, longDesc, time, date, spinner);

                //переключемся в MainActivity
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;

            case R.id.btnCancelEdit:
                //переключемся в MainActivity
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;

            case R.id.btnSetTimeEdit:
                setTime(tvTime);
                break;

            case R.id.btnSetDateEdit:
                setDate(tvDate);
                break;

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // закрываем подключение к БД
        db.close();

    }

    //    ------------------------------------------------------------------------------------------
    // отображаем диалоговое окно для выбора даты
    public void setDate(View v) {
        new DatePickerDialog(this, d,
                dateAndTime.get(Calendar.YEAR),
                dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    // отображаем диалоговое окно для выбора времени
    public void setTime(View v) {
        new TimePickerDialog(this, t,
                dateAndTime.get(Calendar.HOUR_OF_DAY),
                dateAndTime.get(Calendar.MINUTE), true)
                .show();
    }

    // установка начальных времени
    private void setInitialTime() {
        tvTime.setText(DateUtils.formatDateTime(this,
                dateAndTime.getTimeInMillis(), DateUtils.FORMAT_SHOW_TIME));
    }

    // установка начальных даты
    private void setInitialDate() {
        tvDate.setText(DateUtils.formatDateTime(this,
                dateAndTime.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR));
    }

    // установка обработчика выбора времени
    TimePickerDialog.OnTimeSetListener t = new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            dateAndTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            dateAndTime.set(Calendar.MINUTE, minute);
            setInitialTime();
        }
    };

    // установка обработчика выбора даты
    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setInitialDate();
        }
    };

}
