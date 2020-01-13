package cn.xuanfs.forum.controller;

import cn.xuanfs.forum.entity.Question;
import cn.xuanfs.forum.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 提问
 * @author xzj
 */
@Controller
public class PublishController {



    @GetMapping("/publish")
    public String toPublish(){
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(Question question, HttpServletRequest request, Model model){
        System.out.println("question:"+question);
        //判断标题和问题是否为空
        if(question.getTitle()==""||question.getDescription()==""){
            model.addAttribute("title",question.getTitle());
            model.addAttribute("description",question.getDescription());
            model.addAttribute("msg","标题或问题不能为空");
            return "publish";
        }
        Date date = new Date();
        question.setGmtCreate(date);
        question.setGmtModified(date);

        User user = (User)request.getSession().getAttribute("user");
        if(user == null){
            model.addAttribute("msg","用户未登陆");
            return "publish";
        }
        question.setCreator(user.getId());
        model.addAttribute("msg","发布成功");

        return "publish";
    }
}
