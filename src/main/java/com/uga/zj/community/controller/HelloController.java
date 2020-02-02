package com.uga.zj.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HelloController {
    @GetMapping("/hello")
    public String hello(@RequestParam(name="para1") String name, Model model){
        model.addAttribute("Name",name);
        return "dnm";
    }
}
