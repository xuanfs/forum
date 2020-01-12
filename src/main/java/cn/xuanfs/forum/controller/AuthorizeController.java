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

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

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
            //登录成功 写cookie 和session
            User user = new User();
            user.setToken(UUID.randomUUID().toString());
            user.setName(gitUser.getName());
            user.setAccoutId(String.valueOf(gitUser.getId()));

            Date date = new Date();
            String format = sdf.format(date);
            try {
                user.setGmtCreate(sdf.parse(format));
                user.setGmtModified(user.getGmtCreate());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            userMapper.insert(user);
            response.addCookie(new Cookie("token", token));
            request.getSession().setAttribute("user",user);
            request.getSession().setAttribute("username",user.getName());
            request.getSession().setMaxInactiveInterval(30);
            return "redirect:/";
        }else{
            //登录失败 重新登录
            return "redirect:/";
        }
    }
}
