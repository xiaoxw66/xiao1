package com.xiao.common.util;

import java.util.UUID;

public class UUIDUtil {

    /**
     * 获取32位uuid
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "").toUpperCase();
    }
}
