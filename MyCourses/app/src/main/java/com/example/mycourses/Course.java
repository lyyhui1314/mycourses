package com.example.mycourses;

import java.io.Serializable;

public class Course implements Serializable {

    private int cid;
    private String courseName;
    private String teacher;
    private String classRoom;
    private int day;
    private int classStart;
    private int classEnd;

    public Course() {
    }

    public Course(int cid, String courseName, String teacher, String classRoom, int day, int classStart, int classEnd) {
        this.cid = cid;
        this.courseName = courseName;
        this.teacher = teacher;
        this.classRoom = classRoom;
        this.day = day;
        this.classStart = classStart;
        this.classEnd = classEnd;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getClassRoom() {
        return classRoom;
    }

    public void setClassRoom(String classRoom) {
        this.classRoom = classRoom;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getStart() {
        return classStart;
    }

    public void setStart(int classStart) {
        this.classEnd = classStart;
    }

    public int getEnd() {
        return classEnd;
    }

    public void setEnd(int classEnd) {
        this.classEnd = classEnd;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public int getClassStart() {
        return classStart;
    }

    public void setClassStart(int classStart) {
        this.classStart = classStart;
    }

    public int getClassEnd() {
        return classEnd;
    }

    public void setClassEnd(int classEnd) {
        this.classEnd = classEnd;
    }
}
