package com.xiao.common.dto;

import lombok.Data;

@Data
public class ResponseData<T> extends BasicDto {

    private static final long serialVersionUID = -3439777533058560914L;
    private String code;

    private String msg;

    private T data;

    public ResponseData() {
        super();
    }

    public ResponseData(String code) {
        super();
        this.code = code;
    }

    public ResponseData(String code, String msg) {
        super();
        this.code = code;
        this.msg = msg;
    }

    public ResponseData(T data, String code, String msg) {
        super();
        this.data = data;
        this.code = code;
        this.msg = msg;
    }

    public ResponseData setCodeMsg(String code, String msg) {
        this.code = code;
        this.msg = msg;
        return this;
    }

    public ResponseData setCodeMsgData(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        return this;
    }

}
