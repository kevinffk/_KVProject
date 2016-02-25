package com.kv.iprojectlib.utils;

import java.util.List;

public class ListUitls {

    public static boolean isEmpty(List list) {
        return list == null || (list != null && list.isEmpty());
    }
}
