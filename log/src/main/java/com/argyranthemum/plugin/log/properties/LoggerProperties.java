/**
 * Copyright  2019  weibo
 * All Right Reserved.
 */
package com.argyranthemum.plugin.log.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Description: LoggerProperties
 * @Author: liubo20
 * @CreateTime: 2019-02-15 11:13
 */
@ConfigurationProperties(prefix = "logger.mapping.path")
public class LoggerProperties {

    private String get = "logger/get";

    private String set = "logger/set";

    public String getGet() {
        return get;
    }

    public void setGet(String get) {
        this.get = get;
    }

    public String getSet() {
        return set;
    }

    public void setSet(String set) {
        this.set = set;
    }
}
