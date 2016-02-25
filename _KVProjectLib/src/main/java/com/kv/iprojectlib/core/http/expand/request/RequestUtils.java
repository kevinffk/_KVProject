package com.kv.iprojectlib.core.http.expand.request;

import java.util.Map;

public class RequestUtils {

    public static String getPrintFormRequest(Object obj) {
        if (obj instanceof String) {
            return String.valueOf(obj);
        } else if (obj instanceof Map) {
            Map<String, String> formMap = (Map<String, String>) obj;
            String print = "[";
            if (formMap != null) {
                for (String key : formMap.keySet()) {
                    print += key + ":";
                    print += formMap.get(key) + ",";
                }
                print.substring(0, print.length() - 1);
            }
            print += "]";
            return print;
        }
        return null;
    }
}
