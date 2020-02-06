package com.uga.zj.community.controller;

import com.uga.zj.community.DataTransferObject.AccessToken;
import com.uga.zj.community.DataTransferObject.GithubUser;
import com.uga.zj.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @GetMapping("/callback")
    public String callback(@RequestParam(name="code") String code,
                           @RequestParam(name="state") String state) {
        AccessToken accessToken = new AccessToken();
        accessToken.setCode(code);
        accessToken.setState(state);
        accessToken.setRedirect_uri(redirectUrl);
        accessToken.setClient_id(clientId);
        accessToken.setClient_secret(clientSecret);
        String accTokenStr = githubProvider.getAccessToken(accessToken);
        GithubUser user = githubProvider.getUser(accTokenStr);
        System.out.print(user.getName());
        return "index";
    }
}
