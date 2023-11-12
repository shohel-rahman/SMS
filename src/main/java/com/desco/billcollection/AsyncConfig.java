package com.desco.billcollection;

import java.util.concurrent.Executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class AsyncConfig {
	
	Logger logger =LoggerFactory.getLogger(AsyncConfig.class);
	
    @Bean (name = "taskExecutor")
    public Executor taskExecutor() {
       logger.debug("Creating Async Task Executor");
        final ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(20);
        executor.setQueueCapacity(40);
        executor.setThreadNamePrefix("SMS Thread-");
        executor.initialize();
        return executor;
    }

}
