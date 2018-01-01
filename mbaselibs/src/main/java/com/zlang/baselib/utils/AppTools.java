package com.zlang.baselib.utils;

/**
 * Created by zlang on 2017/12/28.
 */

public class AppTools {

    /**
     * 判断对象是否为空，是否等于对象“null”，是否是空对象
     * @param
     * @return
     */
    public static boolean isEmpty(Object obj) {
        return (obj == null || "".equals(obj) || "null".equals(obj));
    }
    /**
     * 判断字符串是否为空，是否等于字符串“null”，是否是空格字符串
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0 || "null".equals(str);
    }

}
