package com.example.mwork;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;


public class CreateJobActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String[] data = new String[]{
            "не приступал",
            "в работе",
            "выполнена",
            "приостановленна",
            "отменена"};

    public static String[] getData() {
        return data;
    }


    Spinner spCreate;

    EditText etShortDescription;
    EditText etLongDescription;

    TextView tvTime;
    TextView tvDate;
    Button btnSetTime;
    Button btnSetDate;


    Button btnSave;
    Button btnCancel;

    Intent intent;
    DB db;
    SQLiteDatabase mDB;

    Calendar dateAndTime = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_job);

        etShortDescription = (EditText) findViewById(R.id.etShotDescription);
        etLongDescription = (EditText) findViewById(R.id.etLongDescription);

        tvTime = (TextView) findViewById(R.id.tvTime);
        tvDate = (TextView) findViewById(R.id.tvDate);

        btnSave = (Button) findViewById(R.id.btnSave);
        btnSave.setOnClickListener(this);

        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(this);

        btnSetTime = (Button) findViewById(R.id.btnSetTime);
        btnSetTime.setOnClickListener(this);

        btnSetDate = (Button) findViewById(R.id.btnSetDate);
        btnSetDate.setOnClickListener(this);


//SPINNER-------------------------------------------------------------------------------------------

        // адаптер
        //Создаем адаптер, используем simple_spinner_item в качестве layout для отображения Spinner на экране
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data);

        //А методом setDropDownViewResource указываем какой layout использовать для прорисовки пунктов выпадающего списка.
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spCreate = (Spinner) findViewById(R.id.spinnerCreate);
        spCreate.setAdapter(adapter);

        // выделяем элемент
        spCreate.setSelection(0);
//--------------------------------------------------------------------------------------------------


        db = new DB(this);
        mDB = db.open();

    }

    @Override
    public void onClick(View v) {

        // создаем объект для данных
        ContentValues contentValues = new ContentValues();

        // получаем данные из полей ввода
        String shotDesc = etShortDescription.getText().toString();
        String longDesc = etLongDescription.getText().toString();
        String time = tvTime.getText().toString();
        String date = tvDate.getText().toString();
        String status = spCreate.getSelectedItem().toString();


        switch (v.getId()) {
            case R.id.btnSave:
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

                db.addRec(shotDesc, longDesc, time, date, status);

                //переключемся в MainActivity
                intent = new Intent(this, MainActivity.class);
                setResult(RESULT_OK, intent);
                finish();
                break;

            case R.id.btnCancel:
                //переключемся в MainActivity
                intent = new Intent(this, MainActivity.class);
                setResult(RESULT_CANCELED, intent);
                finish();
                break;

            case R.id.btnSetTime:
                setTime(tvTime);
                break;

            case R.id.btnSetDate:
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

    //    -----------------------------------------------------------------------------------------
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
