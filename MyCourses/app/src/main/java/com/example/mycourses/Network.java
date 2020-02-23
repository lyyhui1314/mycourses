package com.example.mycourses;

import android.os.AsyncTask;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Network {

    public Network() {
    }

    public String accessNet(FormBody.Builder formBody) {
        String url = "http://10.0.2.2/AndroidApp/api/values";
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .post(formBody.build())
                .build();
        Call call = okHttpClient.newCall(request);
        String responseStr = null;
        try {
            Response response = call.execute();
            responseStr = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseStr;
    }

    public int login(String no, String pw) {
        //构造请求表单
        FormBody.Builder formBody = new FormBody.Builder();
        formBody.add("type", "login");      //本次请求的操作类型
        formBody.add("no", no);
        formBody.add("pw", pw);
        //访问网络
        String responseStr = this.accessNet(formBody);
        //解析字符串
        String jsonStr = JSON.parseObject(responseStr, String.class);
        return Integer.parseInt(jsonStr);
    }

    public int register(Usr usr){
        //构造请求表单
        FormBody.Builder formBody = new FormBody.Builder();
        formBody.add("type", "register");      //本次请求的操作类型
        formBody.add("no", usr.getNo());
        formBody.add("pw", usr.getPw());
        formBody.add("school", usr.getSchool());
        formBody.add("major", usr.getMajor());
        //访问网络
        String responseStr = this.accessNet(formBody);
        //解析字符串
        String jsonStr = JSON.parseObject(responseStr, String.class);
        return Integer.parseInt(jsonStr);
    }

    public void getCourseInfo(Integer uid, ArrayList<Course> cUidData) {
        //构造请求表单
        FormBody.Builder formBody = new FormBody.Builder();
        formBody.add("type", "getCourseInfo");
        formBody.add("uid", uid.toString());
        //访问网络
        String responseStr = this.accessNet(formBody);
        //解析字符串
        String jsonStr = JSON.parseObject(responseStr, String.class);
        JSONArray jArray = JSON.parseArray(jsonStr);
        for (int i = 0; i < jArray.size(); i++) {
            JSONObject jObj = jArray.getJSONObject(i);
            int cid = jObj.getIntValue("cid");
            String cn = jObj.getString("coursename");
            String tea = jObj.getString("teacher");
            String cr = jObj.getString("classroom");
            int day = jObj.getIntValue("day");
            int cs = jObj.getIntValue("classstart");
            int ce = jObj.getIntValue("classend");
            Course s = new Course(cid, cn, tea, cr, day, cs, ce);
            cUidData.add(s);
        }
    }

    public int setCourse(String type, Integer uid, Course course) {
        //构造请求表单
        FormBody.Builder formBody = new FormBody.Builder();
        formBody.add("uid", uid.toString());        //当前用户的id
        formBody.add("type", type);      //本次请求的操作类型
        formBody.add("cid", Integer.toString(course.getCid()));     //课程的id
        if (type.equals("addCourse") || type.equals("editCourse")){
            formBody.add("coursename", course.getCourseName());
            formBody.add("teacher", course.getTeacher());
            formBody.add("classroom", course.getClassRoom());
            formBody.add("classstart", Integer.toString(course.getClassStart()));
            formBody.add("classend", Integer.toString(course.getClassEnd()));
            formBody.add("day", Integer.toString(course.getDay()));
        }
        //访问网络
        String responseStr = this.accessNet(formBody);
        //解析字符串
        String jsonStr = JSON.parseObject(responseStr, String.class);
        return Integer.parseInt(jsonStr);
    }
}
