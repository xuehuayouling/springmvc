package com.ysq.test.util;

import java.util.UUID;

public class TextUtil {

    public static boolean isEmpty(String str) {
        return str == null || str == "";
    }

    public static String getUuId() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replace('-', ' ').replaceAll(" ", "");
    }
}
