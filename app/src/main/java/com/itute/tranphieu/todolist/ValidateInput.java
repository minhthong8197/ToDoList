package com.itute.tranphieu.todolist;

public class ValidateInput {
    public static boolean validateGroupName(String inputString) {
        if (inputString.matches("[\\p{L} 0-9]{1,20}"))
            return true;
        else return false;
    }

    public static boolean validateWorkTitle(String inputString) {
        if (inputString.matches("[\\p{L} 0-9]{1,50}"))
            return true;
        else return false;
    }
}
