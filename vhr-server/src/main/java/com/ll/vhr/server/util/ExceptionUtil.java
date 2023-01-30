package com.ll.vhr.server.util;

import cn.hutool.core.util.RandomUtil;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ExceptionUtil {

    public static String getErrorInfoFromException(Exception e) {
        return getErrorInfoFromException(e, RandomUtil.randomString(10));
    }

    public static String getErrorInfoFromException(Exception e, String prefix) {
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);

            return "\r\n" + "错误代号:" + prefix + "\r\n" + sw.toString() + "\r\n";

        } catch (Exception e2) {
            return "bad getErrorInfoFromException";
        }
    }
}
