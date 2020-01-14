package cn.xuanfs.forum.controller;

import cn.xuanfs.forum.entity.AccessToken;
import cn.xuanfs.forum.entity.GithubUser;
import cn.xuanfs.forum.entity.User;
import cn.xuanfs.forum.mapper.UserMapper;
import cn.xuanfs.forum.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * 验证
 * @author xzj
 */
@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;

    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.client.secert}")
    private String clientSecret;

    @Value("${github.redirect.uri}")
    private String redirectUri;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/callback")
    public String callback(String code, String state, HttpServletRequest request, HttpServletResponse response){

        AccessToken accessToken = new AccessToken();
        accessToken.setClient_id(clientId);
        accessToken.setClient_secret(clientSecret);
        accessToken.setRedirectUri(redirectUri);
        accessToken.setState(state);
        accessToken.setCode(code);

        String token = githubProvider.getAccessToken(accessToken);
        GithubUser gitUser = githubProvider.getUser(token);

        if(gitUser != null){
            User isUser = userMapper.findByAccoutId(String.valueOf(gitUser.getId()));
            if(isUser == null){
                //登录成功 写cookie 和session
                User user = new User();
                user.setToken(UUID.randomUUID().toString());
                user.setName(gitUser.getName());
                user.setAccoutId(String.valueOf(gitUser.getId()));
                user.setAvatarUrl(gitUser.getAvatar_url());
                Date date = new Date();
                user.setGmtCreate(date);
                user.setGmtModified(date);
                userMapper.insert(user);
                request.getSession().setAttribute("user",user);
                request.getSession().setMaxInactiveInterval(30*60);
            }else{
                request.getSession().setAttribute("user",isUser);
                request.getSession().setMaxInactiveInterval(30*60);
            }



            return "redirect:/";
        }else{
            //登录失败 重新登录
            return "redirect:/";
        }
    }
}
