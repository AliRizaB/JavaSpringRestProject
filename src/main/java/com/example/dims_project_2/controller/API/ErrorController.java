package com.example.dims_project_2.controller.API;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorController {

    @GetMapping("/access_denied")
    public String accessDenied() {
        return "access_denied";
    }
}