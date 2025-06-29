package com.virtualthread;

import com.sun.management.OperatingSystemMXBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

/**
 * File is created by andreychernenko at 14.06.2025
 */


@Slf4j
@RestController
@RequestMapping("/api")
public class ThreadController {

    private final MetricCollector metricCollector;

    public ThreadController(MetricCollector metricCollector) {
        this.metricCollector = metricCollector;
        log.info("Jvm Properties: {}",
                System.getProperties()
                        .entrySet()
                        .stream()
                        .map(entry -> entry.getKey() + "=" + entry.getValue())
                        .collect(Collectors.joining("\n")));
    }

    @GetMapping("/get-file")
    public String getFile() throws IOException {
        String content = String.join("", Files.readAllLines(Paths.get(".idea/6mb-examplefile-com.txt")));
        this.metricCollector.collectProcessMetrics();
        return "Handled by: " + Thread.currentThread() + "content: " + content.substring(0, 10);
    }

    @GetMapping("/get-metrics")
    public ResponseEntity<Map<String, Object>> getMetrics() {
        return ResponseEntity.ok(
                Map.of("Avg heap memory",  this.metricCollector.getAvgHeap(),
                        "Avg CPU load", this.metricCollector.getAvgCPULoad()));
    }
}