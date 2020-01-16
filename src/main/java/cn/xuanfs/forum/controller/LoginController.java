package cn.xuanfs.forum.controller;

import cn.xuanfs.forum.entity.User;
import cn.xuanfs.forum.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xzj
 */
@Controller
public class LoginController {

    private Map<String,String> map = new HashMap<>();

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/login")
    private String toLogin(){
        return "login";
    }

    @PostMapping("/login")
    private String login(String username, String password,
                         HttpServletRequest request, Model model){
        System.out.println("username:"+username+"\tpassword:"+password);
        map.clear();
        map.put("username",username);
        map.put("password",password);

        User user = userMapper.login(map);
        if(user != null){
            Date date = new Date();
            user.setGmtModified(date);
            userMapper.update(user);
            request.getSession().setAttribute("user",user);
            request.getSession().setMaxInactiveInterval(30*60);
            return "redirect:/";
        }else{
            model.addAttribute("msg","用户名或密码错误");
            return "login";
        }


    }
}
