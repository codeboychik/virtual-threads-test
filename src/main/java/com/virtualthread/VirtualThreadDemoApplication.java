package com.virtualthread;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;


/**
 * File is created by andreychernenko at 14.06.2025
 */
@Slf4j
@SpringBootApplication
public class VirtualThreadDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(VirtualThreadDemoApplication.class, args);
    }

    @Bean
    @Qualifier("simpleAsyncThreadExecutor")
    public TaskExecutor simpleAsyncThreadExecutor() {
        return new SimpleAsyncTaskExecutor();
    }

    @Bean("threadPoolTaskExecutor")
    public TaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        log.info("""
                CorePoolSize: {}
                MaximumPoolSize: {}
                PoolSize: {}
                """,
                executor.getCorePoolSize(),
                executor.getMaxPoolSize(),
                executor.getPoolSize()
        );
        return executor;
    }

    @Bean("virtualThreadExecutor")
    public TaskExecutor virtualThreadExecutor() {
        return Thread::startVirtualThread;
    }
}

