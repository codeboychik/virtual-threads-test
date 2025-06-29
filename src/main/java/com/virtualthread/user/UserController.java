package com.virtualthread.user;

import com.virtualthread.MetricCollector;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * File is created by andreychernenko at 21.06.2025
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserRepository userRepository;
    private final MetricCollector metricCollector;

    public UserController(UserRepository userRepository, MetricCollector metricCollector) {
        this.userRepository = userRepository;
        this.metricCollector = metricCollector;
    }

    // returns random user
    @GetMapping("/get-random")
    public ResponseEntity<User> getUser() {
        this.metricCollector.collectProcessMetrics();
        return ResponseEntity.ok(userRepository.getRandomUser());
    }

}
