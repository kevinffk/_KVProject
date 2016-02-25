package com.kv.iprojectlib.utils;


public class ArrayUtils {

    public static boolean isEmpty(Object [] array) {
        return array == null || (array != null && array.length <= 0);
    }
}
