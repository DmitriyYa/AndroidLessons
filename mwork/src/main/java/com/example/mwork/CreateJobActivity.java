package com.example.mwork;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class CreateJobActivity extends AppCompatActivity implements View.OnClickListener {

    final static String LOG_TAG = "myLog";

    RadioButton rbI;
    RadioButton rbNotI;
//    RadioButton rbGroup;

    EditText etShortDescription;
    EditText etLongDescription;

    RadioButton rbPrioritetMedium;
    RadioButton rbPrioritetStrong;

    TextView tvTime;
    TextView tvDate;
    Button btnSetTime;
    Button btnSetDate;


    Button btnSave;
    Button btnCancel;

    DBHelper dbHelper;
    Intent intent;

    Calendar dateAndTime = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_job);

        rbI = (RadioButton) findViewById(R.id.rbI);
        rbNotI = (RadioButton) findViewById(R.id.rbNotI);
//        rbGroup = (RadioButton) findViewById(R.id.rbGroup);

        etShortDescription = (EditText) findViewById(R.id.etShotDescription);
        etLongDescription = (EditText) findViewById(R.id.etLongDescription);

        rbPrioritetMedium = (RadioButton) findViewById(R.id.rbPrioritetMedium);
        rbPrioritetStrong = (RadioButton) findViewById(R.id.rbPrioritetStrong);

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

        // создаем объект для создания и управления версиями БД
        dbHelper = new DBHelper(this);

    }

    @Override
    public void onClick(View v) {

        // создаем объект для данных
        ContentValues contentValues = new ContentValues();

        // получаем данные из полей ввода
        String executor;
        String shotDesc = etShortDescription.getText().toString();
        String longDesc = etLongDescription.getText().toString();
        String prioritet;
        String time = tvTime.getText().toString();
        String date = tvDate.getText().toString();


        // подключаемся к БД
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        switch (v.getId()) {
            case R.id.btnSave:
                Log.d(LOG_TAG, "--- Insert in job: ---");

                //проверяем установлена ли дата и время выполнения задачи
                if (date.equals("")) {
                    Toast.makeText(this, "заполните дату выполнения задачи", Toast.LENGTH_SHORT).show();
                    break;
                }
                if (time.equals("")) {
                    Toast.makeText(this, "заполните время выполнения задачи", Toast.LENGTH_SHORT).show();
                    break;
                }

                //выясняем исполнителя
                if (rbI.isChecked()) {
                    executor = rbI.getText().toString();
                } else {
                    executor = rbNotI.getText().toString();
                }
//                else {
//                    executor = rbGroup.getText().toString();
//                }

                //выясняем приоритет
                if (rbPrioritetMedium.isChecked()) {
                    prioritet = rbPrioritetMedium.getText().toString();
                } else {
                    prioritet = rbPrioritetStrong.getText().toString();
                }

                contentValues.put("executor", executor);
                contentValues.put("shortDescription", shotDesc);
                contentValues.put("longDescription", longDesc);
                contentValues.put("prioritet", prioritet);
                contentValues.put("time", time);
                contentValues.put("date", date);

                long rowId = db.insert("job", null, contentValues);
                Log.d(LOG_TAG, " row insert, ID: " + rowId);

                //переключемся в MyJobsActivity
                intent = new Intent(this, MyJobsActivity.class);
                setResult(RESULT_OK, intent);
                finish();
                break;

            case R.id.btnCancel:
                //переключемся в MyJobsActivity
                intent = new Intent(this, MyJobsActivity.class);
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

        // закрываем подключение к БД
        dbHelper.close();

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
