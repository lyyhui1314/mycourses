package com.example.mycourses;

import android.content.Intent;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText noEt;
    private EditText pwEt;
    private String no = "";     //防止空指针异常
    private String pw = "";     //防止空指针异常
    private Integer rCode;      //操作结果，大于0为用户id，等于0为账号不存在，-1为密码错误

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        noEt = findViewById(R.id.noEt_login);
        pwEt = findViewById(R.id.pwEt_login);

        TextView regTv = findViewById(R.id.tv_nowregister);
        regTv.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);//添加下划线
        regTv.setOnClickListener(this);

        TextView forgivepwTv = findViewById(R.id.tv_forgivepasswd);
        forgivepwTv.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);//添加下划线
        forgivepwTv.setOnClickListener(this);

        Button logBt = findViewById(R.id.logBt);
        logBt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.logBt:
                no = noEt.getText().toString();
                pw = pwEt.getText().toString();
                //判断账号密码均非空,执行异步任务访问网络
                if(no.equals(""))
                    Toast.makeText(this, "请输入账号", Toast.LENGTH_LONG).show();
                else if (pw.equals(""))
                    Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
                else{
                    netAsyncTask netAsyncTask = new netAsyncTask();
                    netAsyncTask.execute();
                }
                break;
            case R.id.tv_nowregister:
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
        }
    }

    class netAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            Network network = new Network();
            rCode = network.login(no, pw);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (rCode > 0) {
                Intent intent = new Intent(getApplicationContext(), CourseActivity.class);
                intent.putExtra("uid", rCode);
                startActivity(intent);
            } else if (rCode == 0) {
                Toast.makeText(getApplicationContext(), "账号不存在", Toast.LENGTH_SHORT).show();
            } else if (rCode == -1) {
                Toast.makeText(getApplicationContext(), "密码错误", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "未知错误", Toast.LENGTH_SHORT).show();
            }
        }

    }
}
