package com.example.mycourses;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditCourseActivity extends AppCompatActivity implements View.OnClickListener {
    private Integer uid;     //当前登录用户的id
    private Course cUid;        //当前用户的当前编辑的课程
    private Integer rCode;      //操作结果
    private EditText cnEt;
    private EditText tnEt;
    private EditText crEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_course);

        uid = (Integer) getIntent().getSerializableExtra("uid");
        cUid = (Course)getIntent().getSerializableExtra("course");

        cnEt = findViewById(R.id.course_name_edit);
        tnEt = findViewById(R.id.teacher_name_edit);
        crEt = findViewById(R.id.class_room_edit);

        cnEt.setText(cUid.getCourseName());
        tnEt.setText(cUid.getTeacher());
        crEt.setText(cUid.getClassRoom());

        Button okBt = findViewById(R.id.ok_edit);
        okBt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String courseName = cnEt.getText().toString();
        String teacher = tnEt.getText().toString();
        String classRoom = crEt.getText().toString();

        if (courseName.equals("") || teacher.equals("") || classRoom.equals("")) {
            Toast.makeText(EditCourseActivity.this, "基本课程信息未填写", Toast.LENGTH_SHORT).show();
        } else {
            if (courseName.equals(cUid.getCourseName()) && teacher.equals(cUid.getTeacher()) && classRoom.equals(cUid.getClassRoom())) {
                Intent intent = new Intent(getApplicationContext(), CourseActivity.class);
                startActivity(intent);
                finish();
            } else{
                cUid.setCourseName(courseName);
                cUid.setTeacher(teacher);
                cUid.setClassRoom(classRoom);

                netAsyncTask netAsyncTask = new netAsyncTask();
                netAsyncTask.execute();
            }
        }
    }

    class netAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            Network network = new Network();
            rCode = network.setCourse("editCourse", uid, cUid);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (rCode > 0){
                Intent intent = new Intent(getApplicationContext(), CourseActivity.class);
                cUid.setCid(rCode);
                intent.putExtra("course", cUid);
                setResult(1, intent);
                finish();
            }else{
                Toast.makeText(getApplicationContext(), "未知错误", Toast.LENGTH_SHORT).show();
            }
        }
    }


}
