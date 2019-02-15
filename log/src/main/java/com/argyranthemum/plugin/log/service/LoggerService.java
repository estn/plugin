/**
 * Copyright  2019  weibo
 * All Right Reserved.
 */
package com.argyranthemum.plugin.log.service;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import com.argyranthemum.plugin.log.endpoint.LoggerEndpoint;
import com.argyranthemum.plugin.log.properties.LoggerProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.condition.RequestMethodsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;

/**
 * @Description: LoggerDynamicConfig
 * @Author: liubo20
 * @CreateTime: 2019-02-15 09:10
 */
public class LoggerService {

    private static final Logger log = LoggerFactory.getLogger(LoggerService.class);

    private WebApplicationContext webApplicationContext;
    private LoggerProperties loggerProperties;

    public LoggerService(WebApplicationContext webApplicationContext, LoggerProperties loggerProperties) {
        this.webApplicationContext = webApplicationContext;
        this.loggerProperties = loggerProperties;
        registerEndPoint();
    }

    private void registerEndPoint() {
        RequestMappingHandlerMapping requestMappingHandlerMapping = webApplicationContext.getBean(RequestMappingHandlerMapping.class);
        RequestMethodsRequestCondition requestMethodsRequestCondition = new RequestMethodsRequestCondition(RequestMethod.GET);

        //注册get方法
        Method getMethod = ReflectionUtils.findMethod(LoggerEndpoint.class, "get", String.class); // 找到处理该路由的方法
        PatternsRequestCondition getPatternsRequestCondition = new PatternsRequestCondition(loggerProperties.getGet());
        RequestMappingInfo getMappingInfo = new RequestMappingInfo(getPatternsRequestCondition, requestMethodsRequestCondition, null, null, null, null, null);
        requestMappingHandlerMapping.registerMapping(getMappingInfo, "loggerEndpoint", getMethod); // 注册映射处理

        //注册set方法
        Method setMethod = ReflectionUtils.findMethod(LoggerEndpoint.class, "set", String.class, String.class, Integer.class); // 找到处理该路由的方法
        PatternsRequestCondition setPatternsRequestCondition = new PatternsRequestCondition(loggerProperties.getSet());
        RequestMappingInfo setMappingInfo = new RequestMappingInfo(setPatternsRequestCondition, requestMethodsRequestCondition, null, null, null, null, null);
        requestMappingHandlerMapping.registerMapping(setMappingInfo, "loggerEndpoint", setMethod); // 注册映射处理
    }

    /**
     * 设置logger日志级别
     *
     * @param name     日志名称
     * @param level    级别
     * @param duration 持续时长(单位:秒). -1表示无限
     */
    public Boolean set(String name, Level level, Integer duration) {
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        ch.qos.logback.classic.Logger logger = loggerContext.getLogger(name);
        if (logger == null) {
            return false;
        }
        Level former = logger.getLevel();
        log.info("set log level. name:{} to level: {} -> {}, duration:{}.", name, former, level, duration);

        if (duration != null && duration > 0) {
            ScheduledService.delaySecond(() -> logger.setLevel(former), duration);
        }

        logger.setLevel(level);
        return true;
    }

    /**
     * 获取指定logger日志级别
     *
     * @param name logger名称
     */
    public Object get(String name) {
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        ch.qos.logback.classic.Logger logger = loggerContext.getLogger(name);
        if (logger == null) {
            return "not found logger for name: " + name;
        }
        return logger.getLevel();
    }

}
