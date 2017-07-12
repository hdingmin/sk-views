package com.hdingmin.skviewsdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.sk_linearlayout).setOnClickListener(this);
        findViewById(R.id.sk_point).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sk_linearlayout:
                Intent intent = new Intent(this,SkLinearLayoutDemo.class);
                startActivity(intent);
                break;
            case R.id.sk_point:
                Intent intent2 = new Intent(this,SkPointDemo.class);
                startActivity(intent2);
                break;
            default:break;
        }
    }
}
