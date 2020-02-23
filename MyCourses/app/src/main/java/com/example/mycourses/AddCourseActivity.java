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

public class AddCourseActivity extends AppCompatActivity implements View.OnClickListener {
    private Integer uid;     //当前登录用户的id
    private Integer rCode;      //操作结果
    private EditText cnEt;
    private EditText teaEt;
    private EditText crEt;
    private EditText dayEt;
    private EditText csEt;
    private EditText ceEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
        setFinishOnTouchOutside(false);

        uid = (Integer) getIntent().getSerializableExtra("uid");

        cnEt = findViewById(R.id.course_name_add);
        teaEt = findViewById(R.id.teacher_name_add);
        crEt = findViewById(R.id.class_room_add);
        dayEt = findViewById(R.id.week_add);
        csEt = findViewById(R.id.classes_begin_add);
        ceEt = findViewById(R.id.classes_over_add);

        Button okButton = (Button) findViewById(R.id.ok_add);
        okButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String courseName = cnEt.getText().toString();
        String teacher = teaEt.getText().toString();
        String classRoom = crEt.getText().toString();
        String day = dayEt.getText().toString();
        String start = csEt.getText().toString();
        String end = ceEt.getText().toString();

        if (courseName.equals("") || teacher.equals("") || classRoom.equals("") ||  day.equals("") || start.equals("") || end.equals("")) {
            Toast.makeText(AddCourseActivity.this, "基本课程信息未填写~", Toast.LENGTH_SHORT).show();
        } else {
            Integer dayNum = Integer.parseInt(day);
            Integer startNum = Integer.parseInt(start);
            Integer endNum = Integer.parseInt(end);
            if (dayNum < 1 || dayNum > 7) {
                Toast.makeText(AddCourseActivity.this, "每周只有七天~", Toast.LENGTH_SHORT).show();
            } else if (startNum < 1 || startNum > 12 || endNum < 1 || endNum > 12) {
                    Toast.makeText(AddCourseActivity.this, "每天只有1-12节课~", Toast.LENGTH_SHORT).show();
                }else if (startNum > endNum) {
                Toast.makeText(AddCourseActivity.this, "开始节数不得大于结束节数~", Toast.LENGTH_SHORT).show();
            } else {
                    Course course = new Course(0, courseName, teacher, classRoom,
                            Integer.valueOf(day), Integer.valueOf(start), Integer.valueOf(end));
                    netAsyncTask netAsyncTask = new netAsyncTask();
                    netAsyncTask.execute(course);
                }
            }
        }

    class netAsyncTask extends AsyncTask<Course, Void, Course> {

        @Override
        protected Course doInBackground(Course... courses) {
            Network network = new Network();
            rCode = network.setCourse("addCourse", uid, courses[0]);
            return courses[0];
        }

        @Override
        protected void onPostExecute(Course course) {
            super.onPostExecute(course);
            if (rCode > 0) {
                Intent intent = new Intent(getApplicationContext(), CourseActivity.class);
                course.setCid(rCode);
                intent.putExtra("course", course);
                setResult(0, intent);
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "此时间段已有课程", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

