package com.virtualthread;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.TaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;

/**
 * File is created by andreychernenko at 14.06.2025
 */


@Slf4j
@RestController
@RequestMapping("/api")
public class ThreadController {

    private final TaskExecutor simpleAsyncThreadExecutor;
    private final TaskExecutor virtualThreadExecutor;
    private final TaskExecutor threadPoolTaskExecutor;

    public ThreadController(
            @Qualifier("simpleAsyncThreadExecutor") TaskExecutor simpleAsyncThreadExecutor,
            @Qualifier("virtualThreadExecutor") TaskExecutor virtualThreadExecutor,
            @Qualifier("threadPoolTaskExecutor") TaskExecutor threadPoolTaskExecutor
    ) {
        this.simpleAsyncThreadExecutor = simpleAsyncThreadExecutor;
        this.virtualThreadExecutor = virtualThreadExecutor;
        this.threadPoolTaskExecutor = threadPoolTaskExecutor;
    }

    @GetMapping("/pool")
    public CompletableFuture<String> pool(){

        CompletableFuture<String> future = new CompletableFuture<>();
        threadPoolTaskExecutor.execute(() -> {
            try {
                Thread.sleep(1500);
                log.info("Handled by platform thread: {}", Thread.currentThread());
                future.complete("Handled by platform thread: " + Thread.currentThread());
            } catch (InterruptedException e) {
                future.completeExceptionally(e);
            }
        });
        return future;
    }

    @GetMapping("/async")
    public CompletableFuture<String> simpleAsync() {

        CompletableFuture<String> future = new CompletableFuture<>();
        simpleAsyncThreadExecutor.execute(() -> {
            try {
                Thread.sleep(1500);
                log.info("Handled by platform thread: {}", Thread.currentThread());
                future.complete("Handled by platform thread: " + Thread.currentThread());
            } catch (InterruptedException e) {
                future.completeExceptionally(e);
            }
        });
        return future;
    }

    @GetMapping("/virtual")
    public CompletableFuture<String> virtual() {
        CompletableFuture<String> future = new CompletableFuture<>();
        virtualThreadExecutor.execute(() -> {
            try {
                Thread.sleep(1500);
                log.info("Handled by virtual thread: {}", Thread.currentThread());
                future.complete("Handled by virtual thread: " + Thread.currentThread());
            } catch (InterruptedException e) {
                future.completeExceptionally(e);
            }
        });
        return future;
    }
}

