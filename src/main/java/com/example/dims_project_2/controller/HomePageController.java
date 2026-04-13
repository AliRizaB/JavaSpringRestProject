package com.example.dims_project_2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomePageController {
    @GetMapping("/")
    public String getHome(){
        return "index";
    }

    @GetMapping("/login")
    public String getLogin(){
        return "Login/login";
    }

}
