package com.xiao.common.util;

import java.util.List;
import java.util.Map;

/**
 * 元素校验类
 */
public class ItemValidate {

    /**
     * 工具类禁止实例化
     */
    private ItemValidate() {

    }

    /**
     * 判断对象是否为空(Object,List,Map)
     *
     * @param object
     * @return
     */
    public static boolean isEmpty(Object object) {
        boolean flag = false;
        if (object == null) {
            flag = true;
        } else if (object instanceof List) {
            flag = ((List<?>) object).isEmpty();
        } else if (object instanceof Map) {
            flag = ((Map<?, ?>) object).isEmpty();
        }
        return flag;
    }

    public static boolean isNotEmpty(Object object) {
        return !isEmpty(object);
    }

}
