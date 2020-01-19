package cn.xuanfs.forum.controller;

import cn.xuanfs.forum.entity.Notification;
import cn.xuanfs.forum.entity.Question;
import cn.xuanfs.forum.entity.User;
import cn.xuanfs.forum.mapper.UserMapper;
import cn.xuanfs.forum.service.NotificationService;
import cn.xuanfs.forum.service.QuestionService;
import cn.xuanfs.forum.util.ForumUtil;
import cn.xuanfs.forum.util.NotificationStatusEnum;
import cn.xuanfs.forum.util.PageNumUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author xzj
 */
@Controller
public class ProfileController {
    
    @Autowired
    private QuestionService questionService;

    @Autowired
    private ForumUtil forumUtil;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private PageNumUtil pageNumUtil;
    
    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name = "action")String action,
                          Model model, HttpServletRequest request,
                          @RequestParam(name = "pageNumber",required = false,defaultValue = "1")int pageNumber){
        User user = (User) request.getSession().getAttribute("user");
        if(user == null){
            return "redirect:/";
        }
        Page page = PageHelper.startPage(pageNumber, 5);
        List<Notification> newNotifyByReceiver = notificationService.findNewNotifyByReceiver(user.getId());
        if("questions".equals(action)){
            Page page1 = PageHelper.startPage(pageNumber, 5);
            List<Question> questions = questionService.listByCreator(String.valueOf(user.getId()));
            List arrayList = pageNumUtil.getPageNub(page1);
            model.addAttribute("pageMax",page1.getPages());
            model.addAttribute("questions",questions);
            model.addAttribute("pages",arrayList);
            model.addAttribute("page",page1.getPageNum());
            model.addAttribute("section","question");
            model.addAttribute("sectionName","我的提问");
        }else if ("replies".equals(action)){
            List<Notification> notificationList = forumUtil.notificationComplete(newNotifyByReceiver);
            List arrayList = pageNumUtil.getPageNub(page);
            model.addAttribute("pageMax",page.getPages());
            model.addAttribute("newNotify",notificationList);
            model.addAttribute("pages",arrayList);
            model.addAttribute("page",page.getPageNum());
            model.addAttribute("section","replies");
            model.addAttribute("sectionName","最新回复");
        }
        List<Notification> collect = newNotifyByReceiver.stream().filter(notification ->
                notification.getStatus() == 0
        ).collect(Collectors.toList());
        request.getSession().setAttribute("notifyNub",collect.size());
        return "profile";

    }
}
