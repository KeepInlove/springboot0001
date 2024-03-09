package com.example.springboot.common;

public interface Constants {

    String CODE_200 = "200"; //成功
    String CODE_401 = "401";  // 权限不足
    String CODE_400 = "400";  // 参数错误
    String CODE_500 = "500"; // 系统错误
    String CODE_605 = "605"; // 其他业务异常

    String DICT_TYPE_ICON = "icon";

    public static class OrderCheckStatus {
        //待支付
        public static final int  INIT= 1;
        public static final int PAY = 2;
        public static final int EXPRESS = 3;
        public static final int  SUCCESS= 4;

    }
}
