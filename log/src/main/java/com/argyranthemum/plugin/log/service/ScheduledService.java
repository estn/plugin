/**
 * Copyright  2019  weibo
 * All Right Reserved.
 */
package com.argyranthemum.plugin.log.service;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @Description: ScheduledService
 * @Author: liubo20
 * @CreateTime: 2019-02-15 09:20
 */
public class ScheduledService {

    private static ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();

    public static void delaySecond(Runnable command, long delay) {
        service.schedule(command, delay, TimeUnit.SECONDS);
    }
}
