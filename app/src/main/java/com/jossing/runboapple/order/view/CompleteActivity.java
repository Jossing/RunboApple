package com.jossing.runboapple.order.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.jossing.runboapple.R;
import com.jossing.runboapple.main.view.MainActivity;

/**
 * Created by Somnus on 2017/4/14.
 */

public class CompleteActivity extends AppCompatActivity implements View.OnClickListener {
    private Button complete_btn_order,complete_btn_main;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete);
        init();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.complete_btn_main:
                // 回到主页面
                Intent intent_main = new Intent(this, MainActivity.class);
                finish();
                startActivity(intent_main);
                break;
            case R.id.complete_btn_order:
                // 回到订单界面
                break;
        }
    }

    private void init(){
        complete_btn_order = (Button)findViewById(R.id.complete_btn_order);
        complete_btn_order.setOnClickListener(this);
        complete_btn_main = (Button)findViewById(R.id.complete_btn_main);
        complete_btn_main.setOnClickListener(this);
    }

}
