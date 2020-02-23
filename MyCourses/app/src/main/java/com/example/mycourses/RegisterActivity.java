package com.example.mycourses;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText noEt;
    private EditText pwEt;
    private  EditText schoolEt;
    private EditText majorEt;
    private int rCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        noEt = findViewById(R.id.noEt_register);
        pwEt = findViewById(R.id.pwEt_register);
        schoolEt = findViewById(R.id.schoolEt);
        majorEt = findViewById(R.id.majorEt);

        Button regBt = findViewById(R.id.regBt);
        regBt.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        String no = noEt.getText().toString();
        String pw = pwEt.getText().toString();
        String school = schoolEt.getText().toString();
        String major = majorEt.getText().toString();

        if (no.equals("") || pw.equals("") || school.equals("") || major.equals("")){
            Toast.makeText(getApplicationContext(), "基本信息未填写", Toast.LENGTH_SHORT).show();
        }else{
            Usr usr = new Usr(0, no, pw, school, major);
            netAsyncTask netAsyncTask = new netAsyncTask();
            netAsyncTask.execute(usr);

        }
    }

    class netAsyncTask extends AsyncTask<Usr, Void, Void>{

        @Override
        protected Void doInBackground(Usr... usrs){
            Network network = new Network();
            rCode = network.register(usrs[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (rCode > 0){
                Toast.makeText(getApplicationContext(), "注册成功，请登录", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }else if (rCode == 0)
                Toast.makeText(getApplicationContext(), "账号已存在", Toast.LENGTH_SHORT).show();
            else if (rCode == -1)
                Toast.makeText(getApplicationContext(), "注册失败", Toast.LENGTH_SHORT).show();
        }
    }
}
