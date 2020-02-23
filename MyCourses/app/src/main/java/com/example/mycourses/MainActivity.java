package com.example.mycourses;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button mBtnLogin = findViewById(R.id.btn_login);
        Button mBtnRegister = findViewById(R.id.btn_register);

        mBtnLogin.setOnClickListener(this);
        mBtnRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login:
                Intent intentLogin = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intentLogin);
                break;
            case R.id.btn_register:
                Intent intentReg = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intentReg);
                break;
        }
    }
}
