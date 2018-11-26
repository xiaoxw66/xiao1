package com.xiao;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@MapperScan("com.xiao.*.mapper") // myBatis 扫描
@ServletComponentScan
public class XiaoApplication {
    // ServletComponentScan
    // 在 SpringBootApplication 上使用@ServletComponentScan 注解后，Servlet、Filter、Listener
    // 可以直接通过 @WebServlet、@WebFilter、@WebListener 注解自动注册，无需其他代码。
    public static void main(String[] args) {
        SpringApplication.run(XiaoApplication.class, args);
    }
}
