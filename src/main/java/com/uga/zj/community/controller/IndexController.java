package com.uga.zj.community.controller;

import com.uga.zj.community.DataTransferObject.PaginationDTO;
import com.uga.zj.community.DataTransferObject.QuestionDTO;
import com.uga.zj.community.mapper.QuestionMapper;
import com.uga.zj.community.mapper.UserMapper;
import com.uga.zj.community.model.Question;
import com.uga.zj.community.model.User;
import com.uga.zj.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {
    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String index(Model model,
                        @RequestParam(name = "page",defaultValue = "1") Integer page,
                        @RequestParam(name = "size", defaultValue = "5") Integer size){

        PaginationDTO pagination = questionService.list(page,size);
        model.addAttribute("pagination",pagination );
        return "index";
    }
}
