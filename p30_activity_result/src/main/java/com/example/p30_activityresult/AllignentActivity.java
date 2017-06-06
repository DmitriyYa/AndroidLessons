package com.example.p30_activityresult;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;

public class AllignentActivity extends AppCompatActivity implements View.OnClickListener{

    Button btnLeft;
    Button btnRight;
    Button btnCenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allignent);

        btnCenter= (Button) findViewById(R.id.btnCenter);
        btnLeft= (Button) findViewById(R.id.btnLeft);
        btnRight= (Button) findViewById(R.id.btnRight);

        btnCenter.setOnClickListener(this);
        btnLeft.setOnClickListener(this);
        btnRight.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        Intent intent=new Intent();

        switch (v.getId()){
            case R.id.btnCenter:
                intent.putExtra("alignment", Gravity.CENTER);
                break;
            case R.id.btnRight:
                intent.putExtra("alignment", Gravity.RIGHT);
                break;
            case R.id.btnLeft:
                intent.putExtra("alignment", Gravity.LEFT);
                break;
        }
        setResult(RESULT_OK,intent);
        finish();
    }
}
