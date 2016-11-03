package com.ysq.test.util;

/**
 * Created by ysq on 16/10/31.
 */
public class CodeUtil {

    public static String getNext(String code) {
        String startCode = "";
        if (TextUtil.isEmpty(code) || code.length() < 2) {
            return "01";
        } else if (code.endsWith("zz")) {
            throw new StringIndexOutOfBoundsException("当前级别菜单数量已达到最大值!");
        } else {
            if (code.length() > 2) {
                startCode = code.substring(0, code.length() - 2);
                code = code.substring(code.length() - 2);
            }
            char one = code.charAt(0);
            char two = code.charAt(1);
            if (two == 'z') {
                two = '0';
                one = getNextAscii(one);
            } else {
                two = getNextAscii(two);
            }
            return "" + one + two;
        }
    }

    private static char getNextAscii(char o) {
        char two = o;
        int asciiNum = (int) two;
        if (asciiNum == 57) {
            two = 'A';
        } else if (asciiNum == 90) {
            two = 'a';
        } else {
            two = (char) (asciiNum + 1);
        }
        return two;
    }
}
