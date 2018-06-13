package com.itute.tranphieu.todolist.object;

import java.util.Calendar;

public class Time {
    private int hour;
    private int minute;
    private int day;
    private int month;
    private int year;

    public Time() {
    }

    public Time(int hour, int minute) {
        Calendar calendar = Calendar.getInstance();
        this.year = calendar.get(Calendar.YEAR);
        this.month = calendar.get(Calendar.MONTH);
        this.day = calendar.get(Calendar.DATE);
        this.hour = hour;
        this.minute = minute;
    }

    public Time(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
        this.hour=0;
        this.minute=0;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    ////////////////////////////////////////////////////////////////////////
    @Override
    public String toString() {
        String time ="";
        if(hour<10)
            time=time+"0"+hour+":";
        else time=time+hour+":";
        if(minute<10)
            time=time+"0"+minute;
        else time=time+minute;
        return time;
    }
    ////////////////////////////////////////////////////////////////////////
    public String daytoString()
    {
        return day+"/"+month+"/"+year;
    }
}
