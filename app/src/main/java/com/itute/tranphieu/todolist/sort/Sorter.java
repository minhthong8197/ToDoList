package com.itute.tranphieu.todolist.sort;



import com.itute.tranphieu.todolist.object.Work;

import java.util.ArrayList;

public abstract class Sorter {

    ArrayList<Work> listWork;

    public ArrayList sort(ArrayList<Work> listwork) {
        this.listWork = listwork;
        quickSort(listWork, 0, listWork.size() - 1);
        return listWork;
    }

    private void quickSort(ArrayList<Work> listWork, int beginIndex, int endIndex) {
        //neu list truyen vao ko co phan tu thi out
        if (listWork.size() == 0) return;
        if (beginIndex >= endIndex) return;
        //tim vi tri chinh xac cua pivot
        Work pivot = listWork.get(endIndex);
        int i = beginIndex - 1;
        for (int j = beginIndex; j < endIndex; j++) {
            //neu j > pivot
            if (isBigger(listWork.get(j), pivot)) {
                i++;
                if (i < j) {
                    Work temp = listWork.get(i);
                    listWork.set(i, listWork.get(j));
                    listWork.set(j, temp);
                }
            }
        }
        // i+1 la vi tri chinh xac cua pivot
        Work temp = listWork.get(i + 1);
        listWork.set(i + 1, listWork.get(endIndex));
        listWork.set(endIndex, temp);
        // quickSort ben trai va ben phai cua pivot
        this.quickSort(listWork, beginIndex, i);
        this.quickSort(listWork, i + 2, endIndex);
    }

    protected abstract boolean isBigger(Work a, Work b);
}
