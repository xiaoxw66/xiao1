package com.xiao.common.util;

import com.xiao.common.dto.ResponseData;

public class ResponseUtil {

    public static ResponseData getInstance() {
        return new ResponseData();
    }

    public static ResponseData getInstance(String code) {
        return new ResponseData(code);
    }


    public static ResponseData getInstance(String code, String msg) {
        return new ResponseData(code, msg);
    }


}
