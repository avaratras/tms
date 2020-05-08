package com.hcm.tms.config;

import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;


@Configuration
public class MyHttpSessionListener implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        int time = 54000; // 30m
        se.getSession().setMaxInactiveInterval(time);
    }
}
