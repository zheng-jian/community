package com.uga.zj.community.controller;

import com.uga.zj.community.DataTransferObject.AccessToken;
import com.uga.zj.community.DataTransferObject.GithubUser;
import com.uga.zj.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorizeController {
    @Autowired
    private GithubProvider githubProvider;

    @GetMapping("/callback")
    public String callback(@RequestParam(name="code") String code,
                           @RequestParam(name="state") String state) {
        AccessToken accessToken = new AccessToken();
        accessToken.setCode(code);
        accessToken.setState(state);
        accessToken.setRedirect_uri("http://localhost:8080/callback");
        accessToken.setClient_id("47fb4163e1f233df6e00");
        accessToken.setClient_secret("cfe7f7ee11df151a5702082e282ab9c462208f6a");
        String accTokenStr = githubProvider.getAccessToken(accessToken);
        GithubUser user = githubProvider.getUser(accTokenStr);
        System.out.print(user.getName());
        return "index";
    }
}
