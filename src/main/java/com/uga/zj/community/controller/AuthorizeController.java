package com.uga.zj.community.controller;

import com.uga.zj.community.DataTransferObject.AccessToken;
import com.uga.zj.community.DataTransferObject.GithubUser;
import com.uga.zj.community.mapper.UserMapper;
import com.uga.zj.community.model.User;
import com.uga.zj.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class AuthorizeController {
    @Autowired
    private GithubProvider githubProvider;

    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.client.secret}")
    private String clientSecret;

    @Value("${github.redirect.url}")
    private String redirectUrl;

    @Resource
    private UserMapper userMapper;

    @GetMapping("/callback")
    public String callback(@RequestParam(name="code") String code,
                           @RequestParam(name="state") String state,
                           HttpServletRequest request,
                           HttpServletResponse response) {
        AccessToken accessToken = new AccessToken();
        accessToken.setCode(code);
        accessToken.setState(state);
        accessToken.setRedirect_uri(redirectUrl);
        accessToken.setClient_id(clientId);
        accessToken.setClient_secret(clientSecret);
        String accTokenStr = githubProvider.getAccessToken(accessToken);
        GithubUser githubUser = githubProvider.getUser(accTokenStr);
        System.out.print(githubUser.getName());
        if(githubUser !=null){
            //Sign in successfully
            User user = new User();
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(githubUser.getName());
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(System.currentTimeMillis());
            user.setPictureUrl(githubUser.getAvatar_url());
            userMapper.insert(user);
            response.addCookie((new Cookie("token", token )));
            //request.getSession().setAttribute("githubUser",githubUser);
            return "redirect:/";
        }else{
            return "redirect:/ ";
        }
    }
}
