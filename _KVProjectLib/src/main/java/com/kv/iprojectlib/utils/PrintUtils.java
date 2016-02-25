package com.kv.iprojectlib.utils;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * 打印工具类.
 * @version   Revision History
 * <pre>
 * Author      Version       Date        Changes
 * Kevin Feng   1.0        2015年7月1日       Created
 *
 * </pre>
 * @since 1.
 */
public class PrintUtils {

    public String outObjPropertyString(Object obj) {
        StringBuffer sb = new StringBuffer();
        if (null != obj) {
            try {
                this.getPropertyString(obj, sb);
            } catch (Exception e) {
                LogUtil.e("outObjPropertyString is error " + e.getMessage());
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    public String getPropertyString(Object entityName, StringBuffer sb) throws Exception {
        Class c = entityName.getClass();
        Field field[] = c.getDeclaredFields();
        Object obj = null;
        String classname = "";
        Object tempObj = null;
        sb.append("------ " + " begin ------\n");
        for (Field f : field) {

            sb.append(f.getName());
            sb.append(" : ");
            obj = invokeMethod(entityName, f.getName(), f.getType(), null);
            if (null != obj) {
                if (obj.getClass().isArray()) {
                    for (int i = 0; i < Array.getLength(obj); i++) {
                        tempObj = Array.get(obj, i);
                        if (tempObj.getClass().isPrimitive()) {
                            sb.append(tempObj.toString());
                        } else if (tempObj instanceof String) {
                            sb.append(tempObj.toString());
                        } else if (tempObj instanceof Date) {
                            sb.append(tempObj.toString());
                        } else if (tempObj instanceof Number) {
                            sb.append(tempObj.toString());
                        } else {
                            this.getPropertyString(tempObj, sb);
                        }
                    }
                }

                classname = obj.getClass().getName();
                if (classname.indexOf("com.cignacmb.core.model.") > -1) {
                    this.getPropertyString(obj, sb);
                }

            }

            /*if (f.getType() == Address.class) 
            {
                this.getPropertyString(obj , sb);
            }*/

            sb.append(obj);
            sb.append("\n");
        }
        sb.append("------ " + " end ------\n");
        return sb.toString();
    }

    public Object invokeMethod(Object owner, String methodName, Class fieldType, Object[] args) throws Exception {
        Class ownerClass = owner.getClass();

        methodName = methodName.substring(0, 1).toUpperCase() + methodName.substring(1);
        Method method = null;
        try {
            if (fieldType == boolean.class) {
                method = ownerClass.getMethod("is" + methodName);
            } else {
                method = ownerClass.getMethod("get" + methodName);
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            //e.printStackTrace();

            return " can't find 'get" + methodName + "' method";
        }

        return method.invoke(owner);

    }
}
