package com.virtualthread;

import com.sun.management.OperatingSystemMXBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.util.List;

/**
 * File is created by andreychernenko at 27.06.2025
 */

@Slf4j
@Component
public class MetricCollector {
    private List<Long> heapData;
    private List<Double> cpuData;
    private List<Long> memoryData;

    MetricCollector(List<Long> heapData, List<Double> cpuData, List<Long> memoryData) {
        this.heapData = heapData;
        this.cpuData = cpuData;
        this.memoryData = memoryData;
    }

    public void collectProcessMetrics() {
        MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();
        MemoryUsage heapUsage = memoryBean.getHeapMemoryUsage();
        OperatingSystemMXBean osBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        double processCpuLoad = osBean.getProcessCpuLoad();
        long usedHeap = heapUsage.getUsed();
        this.heapData.add(usedHeap);
        if(processCpuLoad > 0 && processCpuLoad < 1.0) {
            this.cpuData.add(processCpuLoad * 100);
        }
    }

    public String getAvgHeap(){
        return this.heapData.stream().mapToLong(Long::longValue).average().getAsDouble() / (1024 * 1024) + " MB";
    }

    public String getAvgCPULoad(){
        return this.cpuData.stream().mapToDouble(Double::doubleValue).average().getAsDouble() + "%";
    }
}
