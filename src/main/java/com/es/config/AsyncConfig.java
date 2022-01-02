package com.es.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * Created on 2021/12/18 0:31
 * 线程池配置类
 * @author Marfack
 */
@Configuration
@EnableAsync
public class AsyncConfig {

    @Bean
    public Executor executor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(100);
        executor.setKeepAliveSeconds(60);
        executor.setThreadNamePrefix("AsyncPool-");
        executor.initialize();
        return executor;
    }
}
