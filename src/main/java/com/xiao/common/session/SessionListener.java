package com.xiao.common.session;

import com.xiao.common.dto.Constants;
import com.xiao.common.util.StringUtil;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.*;

@Slf4j
@WebListener
public class SessionListener implements HttpSessionListener, HttpSessionAttributeListener {

    /**
     * 当给session添加属性时
     * 当向被监听对象中增加一个属性时，web容器就调用事件监听器的attributeAdded方法进行响应，
     * 这个方法接收一个事件类型的参数，监听器可以通过这个参数来获得正在增加属性的域对象和被保存到域中的属性对象
     * 各个域属性监听器中的完整语法定义为：
     * 1 public void attributeAdded(ServletContextAttributeEvent scae)
     * 2 public void attributeAdded (HttpSessionBindingEvent  hsbe)
     * 3 public void attributeAdded (ServletRequestAttributeEvent srae)
     */
    @Override
    public void attributeAdded(HttpSessionBindingEvent httpSessionBindingEvent) {
        log.info("--attributeAdded--");
        //HttpSession session = httpSessionBindingEvent.getSession();
        //log.info("key----:" + httpSessionBindingEvent.getName());
        //log.info("value---:" + httpSessionBindingEvent.getValue());

    }

    /**
     * 当删除被监听对象中的一个属性时，web容器调用事件监听器的attributeRemoved方法进行响应
     * 各个域属性监听器中的完整语法定义为：
     * 1 public void attributeRemoved(ServletContextAttributeEvent scae)
     * 2 public void attributeRemoved (HttpSessionBindingEvent  hsbe)
     * 3 public void attributeRemoved (ServletRequestAttributeEvent srae)
     *
     * @param httpSessionBindingEvent
     */
    @Override
    public void attributeRemoved(HttpSessionBindingEvent httpSessionBindingEvent) {
        log.info("--attributeRemoved--");
    }

    /**
     * 当监听器的域对象中的某个属性被替换时，web容器调用事件监听器的attributeReplaced方法进行响应
     * 各个域属性监听器中的完整语法定义为：
     * 1 public void attributeReplaced(ServletContextAttributeEvent scae)
     * 2 public void attributeReplaced (HttpSessionBindingEvent  hsbe)
     * 3 public void attributeReplaced (ServletRequestAttributeEvent srae)
     *
     * @param httpSessionBindingEvent
     */
    @Override
    public void attributeReplaced(HttpSessionBindingEvent httpSessionBindingEvent) {
        log.info("--attributeReplaced--");
    }

    /**
     * 当session创建时
     *
     * @param event
     */
    @Override
    public void sessionCreated(HttpSessionEvent event) {
        log.info("---sessionCreated----");
        //HttpSession session = event.getSession();
        //ServletContext application = session.getServletContext();
        //// 在application范围由一个HashSet集保存所有的session
        //HashSet sessions = (HashSet) application.getAttribute("sessions");
        //if (sessions == null) {
        //    sessions = new HashSet();
        //    application.setAttribute("sessions", sessions);
        //}
        //// 新创建的session均添加到HashSet集中
        //sessions.add(session);
        //// 可以在别处从application范围中取出sessions集合
        //// 然后使用sessions.size()获取当前活动的session数，即为“在线人数”
    }

    /**
     * 当session被销毁或超时时
     *
     * @param event
     * @throws ClassCastException
     */
    @Override
    public void sessionDestroyed(HttpSessionEvent event) throws ClassCastException {
        HttpSession session = event.getSession();
        String userId = StringUtil.transformNullStr(session.getAttribute(Constants.USER_ID));
        log.info("session销毁之前,将sessionMap中键值对删除userId:{}", userId);
        SessionHelper.removeSession(userId);
        log.info("将sessionMap中键值对删除后的sessionMap:{}", SessionHelper.getInstance());
        //  session.getId()
        // session.getCreationTime()
        // session.getLastAccessedTime()
        // ServletContext application = session.getServletContext();
        // HashSet sessions = (HashSet) application.getAttribute("sessions");
        // 销毁的session均从HashSet集中移除
        //sessions.remove(session);
    }

}
