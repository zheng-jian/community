package com.uga.zj.community.controller;

import com.uga.zj.community.mapper.QuestionMapper;
import com.uga.zj.community.mapper.UserMapper;
import com.uga.zj.community.model.Question;
import com.uga.zj.community.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {
    @Resource
    QuestionMapper questionMapper;

    @Resource
    UserMapper userMapper;

    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(
            @RequestParam(value="title",required=false) String title,
            @RequestParam(value="description",required=false) String description,
            @RequestParam(value="tag",required=false) String tag,
            HttpServletRequest request,
            Model model
    ){
        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);

        if(title == null || title == ""){
            model.addAttribute("error","title can not be void");
            return "publish";
        }
        if(description == null || description == ""){
            model.addAttribute("error","description can not be void");
            return "publish";
        }
        if(tag == null || tag == "") {
            model.addAttribute("error", "tag can not be void");
            return "publish";
        }


        User user = (User) request.getSession().getAttribute("user");
        if(user == null){
            model.addAttribute("error","Not log in");
            return "publish";
        }

        Question question= new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(question.getGmtCreate());
        questionMapper.create(question);
        return "redirect:/";
    }
}
