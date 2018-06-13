package com.itute.tranphieu.todolist.sort;


import com.itute.tranphieu.todolist.object.Work;

public class FinishedSorter extends Sorter {

    @Override
    protected boolean isBigger(Work a, Work b) {
        //kiem tra dau vao
        try {
            // neu a null thi a < b
            if (a == null) return true;
        } catch (Exception e) {
            //e.printStackTrace();
            return true;
        }
        try {
            //neu b null thi b < a
            if (b == null) return false;
        } catch (Exception e) {
            //e.printStackTrace();
            return false;
        }
        Boolean result;
        //if a > b
        if (a.isFinished() == true && b.isFinished() == false) {
            result = true;
        } else
            result = false;
        return !result;//kết quả ngược lại để sắp xếp theo chiều ngược lại
    }
}
