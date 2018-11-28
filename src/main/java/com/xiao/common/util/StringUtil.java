package com.xiao.common.util;

import com.xiao.common.dto.Constants;
import com.xiao.common.dto.ResponseData;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

    /**
     * @Description 判断多个字符串是否为空
     * @Author yexiaomu
     * @Date 2018/11/27 19:47
     * @Param [args]
     * @Return boolean
     **/
    public static boolean isEmptyArr(Object... args) {
        for (Object o : args) {
            if (isEmptyStr(o)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @Description 判断字符串是否为空
     * @Author yexiaomu
     * @Date 2018/11/26 20:45
     * @Param [obj]
     * @Return boolean
     **/
    public static boolean isEmptyStr(Object obj) {
        if (null == obj || "null".equals(obj.toString().trim()) || "".equals(obj.toString().trim())) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @Description 判断字符串是否不为空
     * @Author yexiaomu
     * @Date 2018/11/26 20:45
     * @Param [obj]
     * @Return boolean
     **/
    public static boolean isNotEmptyStr(Object obj) {
        return !isEmptyStr(obj);
    }

    /**
     * @Description obj不为null转字符串
     * @Author yexiaomu
     * @Date 2018/11/26 20:45
     * @Param [obj]
     * @Return java.lang.String
     **/
    public static String transformNullStr(Object obj) {
        if (isEmptyStr(obj) || "null".equals(obj.toString().trim())) {
            return "";
        }
        return obj.toString();
    }

    /**
     * @Description 判断两个值是否相等 相等为true 不相等为false
     * @Author yexiaomu
     * @Date 2018/11/26 20:44
     * @Param [a, b]
     * @Return boolean
     **/
    public static boolean isEqual(Object a, Object b) {
        String aStr = getString(a, "Null");
        String bStr = getString(b, "Null");
        return aStr.equals(bStr);
    }

    /**
     * @Description 判断两个是否不想等 不相等为true 相等为false
     * @Author yexiaomu
     * @Date 2018/11/26 20:44
     * @Param [a, b]
     * @Return boolean
     **/
    public static boolean isNotEqual(Object a, Object b) {
        return !isEqual(a, b);
    }

    /**
     * @Description 功能描述：获取字符串,为空取默认值
     * @Author yexiaomu
     * @Date 2018/11/26 20:44
     * @Param [param, defaultStr]
     * @Return java.lang.String
     **/
    public static String getString(Object param, String defaultStr) {
        if (isEmptyStr(param)) {
            return defaultStr;
        }
        return param.toString();
    }

    /**
     * @Description 判断校验是否通过, code 为0 校验失败 为true
     * @Author yexiaomu
     * @Date 2018/11/26 20:44
     * @Param [responsedata]
     * @Return boolean
     **/
    public static boolean invalid(ResponseData responsedata) {
        return isEqual(Constants.FAILED, responsedata.getCode());
    }

    /**
     * @Description 判断校验是否通过 code为1,校验成功为true
     * @Author yexiaomu
     * @Date 2018/11/26 20:43
     * @Param [responseData]
     * @Return boolean
     **/
    public static boolean validate(ResponseData responseData) {
        return isEqual(Constants.SUCCESS, responseData.getCode());
    }

    /**
     * @Description 邮箱格式校验
     * @Author yexiaomu
     * @Date 2018/11/26 20:43
     * @Param [email]
     * @Return boolean
     **/
    public static boolean checkEmail(String email) {
        try {
            String check = "^[A-Za-z\\d]+([-_.][A-Za-z\\d]+)*@([A-Za-z\\d]+[-.])+[A-Za-z\\d]{2,4}$";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(email);
            return matcher.matches();
        } catch (Exception e) {
            return false;
        }
    }
}
