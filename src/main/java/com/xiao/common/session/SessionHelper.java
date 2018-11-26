package com.xiao.common.session;


import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

public class SessionHelper {

    // 创建一个map存放登录的用户和对应session
    private static Map<String, HttpSession> userSessionMap = new HashMap<>();

    public static Map<String, HttpSession> getInstance() {
        return userSessionMap;
    }

    /**
     * 想map中添加一个session
     *
     * @param userId
     * @param httpSession
     */
    public static void addSession(String userId, HttpSession httpSession) {
        userSessionMap.put(userId, httpSession);
    }

    /**
     * 从map中删除一个session
     *
     * @param userId
     */
    public static void removeSession(String userId) {
        userSessionMap.remove(userId);
    }

    /**
     * 从map中获取一个session
     *
     * @param userId
     * @return
     */
    public static HttpSession getSession(String userId) {
        return userSessionMap.get(userId);
    }
}



