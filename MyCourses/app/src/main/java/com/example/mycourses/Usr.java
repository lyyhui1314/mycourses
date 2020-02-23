package com.example.mycourses;

public class Usr {
    private int uid;
    private String no;
    private String pw;
    private String school;
    private String major;

    public Usr() {
    }

    public Usr(int uid, String no, String pw, String school, String major) {
        this.uid = uid;
        this.no = no;
        this.pw = pw;
        this.school = school;
        this.major = major;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }
}
