package com.itute.tranphieu.todolist;

import com.itute.tranphieu.todolist.object.Work;

import java.util.ArrayList;

public class CleanArrayList {

    public static void CleanSameWork(ArrayList<Work> ArrayList)
    {
        if(ArrayList.size()>1)
        {
            for(int i=0;i<ArrayList.size()-1;i++)
            {
                for(int j=i+1;j<ArrayList.size();j++)
                {
                    if(ArrayList.get(i).equals(ArrayList.get(j)))
                    {
                        ArrayList.remove(i);
                        i--;
                        j--;
                    }
                }
            }
        }
    }

    public static void CleanSameValue(ArrayList<String> stringArrayList)
    {
        if(stringArrayList.size()>1)
        {
            for(int i=0;i<stringArrayList.size()-1;i++)
            {
                for(int j=i+1;j<stringArrayList.size();j++)
                {
                    if(stringArrayList.get(i).equals(stringArrayList.get(j)))
                    {
                        stringArrayList.remove(i);
                        i--;
                        j--;
                    }
                }
            }
        }
    }

}
