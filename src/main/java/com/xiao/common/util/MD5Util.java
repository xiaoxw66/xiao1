package com.xiao.common.util;

import com.xiao.common.baseDto.Constants;
import lombok.extern.slf4j.Slf4j;

import java.security.MessageDigest;

@Slf4j
public class MD5Util {

    private static final String hexDigits[] = {"0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    private static String byteArrayToHexString(byte b[]) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++)
            resultSb.append(byteToHexString(b[i]));

        return resultSb.toString();
    }

    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0)
            n += 256;
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }

    public static String MD5Encode(String origin) {
        return MD5Encode(origin, Constants.UTF8);
    }

    public static String MD5EncodePassword(String account, String password) {
        return MD5Encode(password + account + Constants.PASSWORD_SUFFIX, Constants.UTF8);
    }

    public static String MD5Encode(String origin, String charsetName) {
        String resultString = "";
        try {
            resultString = new String(origin);
            MessageDigest md = MessageDigest.getInstance("MD5");
            if (StringUtil.isEmptyStr(charsetName)) {
                resultString = byteArrayToHexString(md.digest(resultString
                        .getBytes()));
            } else {
                resultString = byteArrayToHexString(md.digest(resultString
                        .getBytes(charsetName)));
            }
        } catch (Exception e) {
            log.error("获取MD5异常", e);
        }
        return resultString.toUpperCase();
    }


    public static void main(String[] args) {
        System.out.println(MD5EncodePassword("admin", "123456")); // 21232F297A57A5A743894A0E4A801FC3
        // 39BC1130E7BEDE0D6D5089BD77C9C976
    }
}
