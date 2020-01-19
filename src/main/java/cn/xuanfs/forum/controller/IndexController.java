package cn.xuanfs.forum.controller;

import cn.xuanfs.forum.entity.Question;
import cn.xuanfs.forum.entity.User;
import cn.xuanfs.forum.mapper.UserMapper;
import cn.xuanfs.forum.service.NotificationService;
import cn.xuanfs.forum.service.QuestionService;
import cn.xuanfs.forum.util.PageNumUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.naming.Name;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 主页控制
 * @author xzj
 */
@Controller
public class IndexController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private PageNumUtil pageNumUtil;

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/")
    public String index(HttpServletRequest request, Model model,
                        @RequestParam(name = "pageNumber", required = false,defaultValue = "1") int pageNumber,
                        @RequestParam(name = "search",required = false,defaultValue = "") String search){

        User user = (User) request.getSession().getAttribute("user");
        if(!StringUtils.isEmpty(search)){
            search = search.replace(" ", "|");
        }


        if (user != null){
            int unread = notificationService.getUnread(user.getId());
            request.getSession().setAttribute("notifyNub",unread);
        }
        Page page = PageHelper.startPage(pageNumber, 5);
        List<Question> questions = questionService.list(search);
        List<Question> hostQuestion = questionService.hostQuestion();

        List arrayList = pageNumUtil.getPageNub(page);

        model.addAttribute("pageMax",page.getPages());
        model.addAttribute("questions",questions);
        model.addAttribute("pages",arrayList);
        model.addAttribute("page",page.getPageNum());
        model.addAttribute("search",search);
        model.addAttribute("hostQuestion",hostQuestion);
        return "index";
    }

    @GetMapping("/logout")
    public String logoout(HttpServletRequest request){
        request.getSession().removeAttribute("user");
        return "redirect:/";
    }

}
