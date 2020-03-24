package com.uga.zj.community.controller;

import com.uga.zj.community.DataTransferObject.QuestionDTO;
import com.uga.zj.community.mapper.QuestionMapper;
import com.uga.zj.community.mapper.UserMapper;
import com.uga.zj.community.model.Question;
import com.uga.zj.community.model.User;
import com.uga.zj.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {

    @Autowired
    QuestionService questionService;

    @GetMapping("/publish/{id}")
    public String edit(@PathVariable(name = "id") Integer id,
                       Model model){
        QuestionDTO question = questionService.getById(id);
        model.addAttribute("title",question.getTitle());
        model.addAttribute("description",question.getDescription());
        model.addAttribute("tag",question.getTag());
        model.addAttribute("id",question.getId());
        return "publish";
    }

    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(
            @RequestParam(value="title",required=false) String title,
            @RequestParam(value="description",required=false) String description,
            @RequestParam(value="tag",required=false) String tag,
            @RequestParam(value="id",required =false) Integer id,
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

        question.setId(id);
        questionService.createOrUpdate(question);
        return "redirect:/";
    }
}
