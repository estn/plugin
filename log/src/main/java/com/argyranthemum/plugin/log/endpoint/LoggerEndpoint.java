/**
 * Copyright  2019  argyranthemum
 * All Right Reserved.
 */
package com.argyranthemum.plugin.log.endpoint;

import ch.qos.logback.classic.Level;
import com.argyranthemum.plugin.log.service.LoggerService;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * @Description: LoggerController
 * @Author: liubo20
 * @CreateTime: 2019-02-15 09:27
 */
public class LoggerEndpoint {

    private LoggerService loggerService;

    public LoggerEndpoint(LoggerService loggerService) {
        this.loggerService = loggerService;
    }

    @ResponseBody
    public Object get(String name) {
        return loggerService.get(name);
    }

    @ResponseBody
    public Object set(String name, String level, Integer duration) {
        return loggerService.set(name, Level.toLevel(level), duration);
    }
}
