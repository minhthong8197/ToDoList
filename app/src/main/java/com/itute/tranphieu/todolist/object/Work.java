package com.itute.tranphieu.todolist.object;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Work implements Serializable {
    private String title;
    private String description;
    private boolean finished;
    private Time time;
    private int repeat;
    private int notify;
    private String key;

    private int id;

    //Constructor

    public Work() {
    }

    public Work(String title) {
        this.title = title;
        this.finished = finished;
        this.id = new Random().nextInt();
    }

    public Work(String key, String title) {
        this.title = title;
        this.finished = false;
        this.key = key;
        this.description = "";
        this.time = null;
        this.repeat = 0;
        this.notify = 0;
        this.id = new Random().nextInt();
    }

    //Getter and Seter

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isFinished() {
        return finished;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public void setTime(int hour, int minute) {
        this.time.setHour(hour);
        this.time.setMinute(minute);
    }

    public void setTime(int day, int month, int year) {
        this.time.setDay(day);
        this.time.setMonth(month);
        this.time.setYear(year);
    }

    public int getRepeat() {
        return repeat;
    }

    public void setRepeat(int repeat) {
        this.repeat = repeat;
    }

    public int getNotify() {
        return notify;
    }

    public void setNotify(int notify) {
        this.notify = notify;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("title", title);
        result.put("description", description);
        result.put("finished", finished);
        result.put("time", time);
        result.put("repeat", repeat);
        result.put("notify", notify);
        result.put("key", key);
        result.put("id", id);
        return result;
    }
}

