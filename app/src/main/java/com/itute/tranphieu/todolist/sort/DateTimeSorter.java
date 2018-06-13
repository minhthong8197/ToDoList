package com.itute.tranphieu.todolist.sort;


import com.itute.tranphieu.todolist.object.Work;

public class DateTimeSorter extends Sorter {
    @Override
    protected boolean isBigger(Work a, Work b) {
        try {
            // neu a null thi a < b
            if (a == null || a.getTime() == null) return false;
        } catch (Exception e) {
            //e.printStackTrace();
            return false;
        }
        try {
            //neu b null thi b < a
            if (b == null || b.getTime() == null) return true;
        } catch (Exception e) {
            //e.printStackTrace();
            return true;
        }

        Boolean result;
        if (a.getTime().getYear() == b.getTime().getYear()) {
            if (a.getTime().getMonth() == b.getTime().getMonth()) {
                if (a.getTime().getDay() == b.getTime().getDay()) {
                    if (a.getTime().getHour() == b.getTime().getHour()) {
                        if (a.getTime().getMinute() == b.getTime().getMinute()) {
                            result = false;//false khi a=b
                        } else
                            result = a.getTime().getMinute() > b.getTime().getMinute();//true khi a>b, false khi a<b
                    } else
                        result = a.getTime().getHour() > b.getTime().getHour();//true khi a>b, false khi a<b
                } else
                    result = a.getTime().getDay() > b.getTime().getDay();//true khi a>b, false khi a<b
            } else
                result = a.getTime().getMonth() > b.getTime().getMonth();//true khi a>b, false khi a<b
        } else
            result = a.getTime().getYear() > b.getTime().getYear();//true khi a>b, false khi a<b
        return result;
    }
}
