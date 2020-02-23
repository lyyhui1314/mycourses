package com.example.mycourses;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class CourseActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener{
    private Integer uid;        //当前登录用户的id
    private RelativeLayout day;     //星期几
    private ArrayList<Course> cUidData;     //当前用户的所有课程
    private Integer rCode;      //操作结果
    private View vCourseItem;       //被选中的课程
    private TextView cidTv;
    private TextView cnTv;
    private TextView tnTv;
    private TextView crTv;
    private TextView dayTv;
    private TextView csTv;
    private TextView ceTv;
    private  int chooseWeek;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        //工具条
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        uid = (Integer) getIntent().getSerializableExtra("uid");
        cUidData = new ArrayList<>();
        netAsyncTask netAsyncTask = new netAsyncTask();
        netAsyncTask.execute("getCourseInfo");

    }

    //创建单个课程视图
    private void createItemCourseView(Course course) {
        int getDay = course.getDay();
        if ((getDay < 1 || getDay > 7) || course.getStart() > course.getEnd()) {
            Toast.makeText(this, "星期填写错误，或开始时间小于结束时间", Toast.LENGTH_LONG).show();
        } else {
            int dayId = 0;
            switch (getDay) {
                case 1:
                    dayId = R.id.Monday;
                    break;
                case 2:
                    dayId = R.id.Tuesday;
                    break;
                case 3:
                    dayId = R.id.Wednesday;
                    break;
                case 4:
                    dayId = R.id.Thursday;
                    break;
                case 5:
                    dayId = R.id.Friday;
                    break;
                case 6:
                    dayId = R.id.Saturday;
                    break;
                case 7:
                    dayId = R.id.Sunday;
                    break;
            }
            day = findViewById(dayId);

            int height = 180;
            vCourseItem = LayoutInflater.from(this).inflate(R.layout.course_card, null);//加载单个课程布局
            vCourseItem.setY(height * (course.getStart() - 1));//设置开始高度，即第几节课开始
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (course.getEnd() - course.getStart() + 1) * height - 8);
            //设置布局高度，即跨多少节课
            vCourseItem.setLayoutParams(params);
            //显示课程信息
            cidTv = vCourseItem.findViewById(R.id.course_id_card);
            cnTv = vCourseItem.findViewById(R.id.course_name_card);
            tnTv = vCourseItem.findViewById(R.id.teacher_name_card);
            crTv = vCourseItem.findViewById(R.id.class_room_card);
            dayTv = vCourseItem.findViewById(R.id.week_card);
            csTv = vCourseItem.findViewById(R.id.classes_begin_card);
            ceTv = vCourseItem.findViewById(R.id.classes_over_card);

            cidTv.setText(Integer.toString(course.getCid()));
            cnTv.setText(course.getCourseName());
            tnTv.setText(course.getTeacher());
            crTv.setText(course.getClassRoom());
            dayTv.setText(Integer.toString(course.getDay()));
            csTv.setText(Integer.toString(course.getClassStart()));
            ceTv.setText(Integer.toString(course.getClassEnd()));

            day.addView(vCourseItem);
            vCourseItem.getBackground().setAlpha(80);
            vCourseItem.setOnClickListener(this);
            vCourseItem.setOnLongClickListener(this);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_courses:
                Intent intent = new Intent(getApplicationContext(), AddCourseActivity.class);
                intent.putExtra("uid", uid);
                startActivityForResult(intent, 0);
                break;
            case R.id.menu_about:
                Intent intent1 = new Intent(getApplicationContext(), AboutActivity.class);
                startActivity(intent1);
                break;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0 && data != null) {
            Course course = (Course) data.getSerializableExtra("course");
            if (resultCode == 0){
                //创建课程表视图
                createItemCourseView(course);
            }else if (resultCode == 1){
                //更新视图
                cidTv = vCourseItem.findViewById(R.id.course_id_card);
                cnTv = vCourseItem.findViewById(R.id.course_name_card);
                tnTv = vCourseItem.findViewById(R.id.teacher_name_card);
                crTv = vCourseItem.findViewById(R.id.class_room_card);

                cidTv.setText(Integer.toString(course.getCid()));
                cnTv.setText(course.getCourseName());
                tnTv.setText(course.getTeacher());
                crTv.setText(course.getClassRoom());
            }
        }
    }

    @Override
    public void onClick(View v) {
        vCourseItem = v;
        cidTv = v.findViewById(R.id.course_id_card);
        cnTv = v.findViewById(R.id.course_name_card);
        tnTv = v.findViewById(R.id.teacher_name_card);
        crTv = v.findViewById(R.id.class_room_card);
        dayTv = v.findViewById(R.id.week_card);
        csTv = v.findViewById(R.id.classes_begin_card);
        ceTv = v.findViewById(R.id.classes_over_card);

        Course course = new Course();
        course.setCid(Integer.parseInt(cidTv.getText().toString()));
        course.setCourseName(cnTv.getText().toString());
        course.setTeacher(tnTv.getText().toString());
        course.setClassRoom(crTv.getText().toString());
        course.setDay(Integer.parseInt(dayTv.getText().toString()));
        course.setClassStart(Integer.parseInt(csTv.getText().toString()));
        course.setClassEnd(Integer.parseInt(ceTv.getText().toString()));


        Intent intent = new Intent(getApplicationContext(), EditCourseActivity.class);
        intent.putExtra("uid", uid);
        intent.putExtra("course", course);
        startActivityForResult(intent, 0);
    }

    @Override
    public boolean onLongClick(View v) {
        vCourseItem = v;
        cidTv = v.findViewById(R.id.course_id_card);
        v.setVisibility(View.GONE);//先隐藏起来
        //从数据库中删除,若删除成功则移除视图
        netAsyncTask netAsyncTask = new netAsyncTask();
        netAsyncTask.execute("delCourse", cidTv.getText().toString());
        return true;
    }

    class netAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... ss) {
            Network network = new Network();
            if (ss[0].equals("getCourseInfo")) {
                network.getCourseInfo(uid, cUidData);
            } else {
                Course c = new Course();
                c.setCid(Integer.parseInt(ss[1]));
                rCode = network.setCourse(ss[0], uid, c);
            }
            return ss[0];
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s.equals("getCourseInfo")) {
                for (Course course : cUidData) {
                    createItemCourseView(course);
                }
            } else if (s.equals("delCourse")) {
                if (rCode > 0)
                    day.removeView(vCourseItem);//移除课程视图
                else
                    Toast.makeText(getApplicationContext(), "删除失败", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
