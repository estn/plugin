package com.argyranthemum.plugin.log;

import ch.qos.logback.classic.LoggerContext;
import com.argyranthemum.plugin.log.properties.LoggerProperties;
import com.argyranthemum.plugin.log.service.LoggerService;
import com.argyranthemum.plugin.log.endpoint.LoggerEndpoint;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;


@Configuration
@ConditionalOnClass(LoggerContext.class)
@EnableConfigurationProperties(LoggerProperties.class)
public class LoggerEndpointAutoConfigure {

    @Resource
    private LoggerProperties loggerProperties;

    @Resource
    private WebApplicationContext webApplicationContext;

    @Bean("loggerEndpoint")
    public LoggerEndpoint endpoint() {
        LoggerService loggerService = new LoggerService(webApplicationContext, loggerProperties);
        return new LoggerEndpoint(loggerService);
    }

}