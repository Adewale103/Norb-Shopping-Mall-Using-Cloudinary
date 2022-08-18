package com.twinkles.Norbs_Shopping_Mall.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HomeController {

    @GetMapping("/home")
    public String showHomePage(){
        return "HOME";
    }

}
