package com.example.demowithtests.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HealthCheckController {
    @GetMapping("/healthcheck")
    public void healthCheck() {

    }

}
