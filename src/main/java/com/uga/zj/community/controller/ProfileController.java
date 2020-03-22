package com.uga.zj.community.controller;

import com.uga.zj.community.DataTransferObject.PaginationDTO;
import com.uga.zj.community.model.User;
import com.uga.zj.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.nio.IntBuffer;

@Controller
public class ProfileController {

    @Autowired
    QuestionService questionService;

    @GetMapping("/profile/{action}")
    public String profile(HttpServletRequest request,
                          @PathVariable(name = "action") String action,
                          Model model,
                          @RequestParam(name = "page", defaultValue = "1")Integer page,
                          @RequestParam(name = "size", defaultValue = "5")Integer size){
        User user = (User)request.getSession().getAttribute("user");
        if(user == null)
            return "redirect:/";

        if("question".equals(action)){
            model.addAttribute("section","questions");
            model.addAttribute("sectionName","My questions");
        }
        else if("replies".equals(action)){
            model.addAttribute("section","replies");
            model.addAttribute("sectionName","Newest replies");
        }

        PaginationDTO paginationDTO = questionService.list(user.getId(), page, size);
        model.addAttribute("pagination",paginationDTO);
        return "profile";
    }
}
