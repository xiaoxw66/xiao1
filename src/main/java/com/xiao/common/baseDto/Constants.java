package com.xiao.common.baseDto;

public class Constants {
    // 访问成功
    public static final String SUCCESS = "0";
    // 查询失败
    public static final String FAILED = "1";
    // 未登录
    public static final String NOT_LOGIN = "2";

    // session中key值
    public static final String BEING_PROCESSED = "processFlag";
    public static final String USER_ID = "userId";
    public static final String USER_DTO = "userDto";
    public static final String USER_NAME = "userName";
    public static final String USER_ACCOUNT = "userAccount";

    // MD5加密用的字符集
    public static final String UTF8 = "UTF-8";

    // 用户密码在后面添加后缀
    public static final String PASSWORD_SUFFIX = "YeMu";

    // 长度校验用的常量
    public static final int STRING_LENGTH_6 = 6;
    public static final int STRING_LENGTH_16 = 16;

    // 道具奖池最大数
    public static final int LOTTERY_POOL_MAX = 1000;

    // 默认字符串
    public static final String NULL_STR = "NullStr";

}
