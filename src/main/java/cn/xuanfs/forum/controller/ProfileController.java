package cn.xuanfs.forum.controller;

import cn.xuanfs.forum.entity.Question;
import cn.xuanfs.forum.entity.User;
import cn.xuanfs.forum.mapper.UserMapper;
import cn.xuanfs.forum.service.QuestionService;
import cn.xuanfs.forum.util.ForumUtil;
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
import java.util.List;

/**
 * @author xzj
 */
@Controller
public class ProfileController {
    
    @Autowired
    private QuestionService questionService;

    @Autowired
    private ForumUtil forumUtil;

    private ArrayList arrayList = new ArrayList();
    
    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name = "action")String action,
                          Model model, HttpServletRequest request,
                          @RequestParam(name = "pageNumber",required = false,defaultValue = "1")int pageNumber){
        User user = (User) request.getSession().getAttribute("user");

        if("questions".equals(action)){
            Page page = PageHelper.startPage(pageNumber, 5);
            List<Question> questions = questionService.listByCreator(String.valueOf(user.getId()));
            arrayList.clear();
            for (int i = 2; i > 0; i--) {
                if(page.getPageNum()-i >= 1){
                    arrayList.add(page.getPageNum()-i);
                }
            }
            for(int i=0;i<3;i++){
                if(page.getPageNum()+i<=page.getPages()){
                    arrayList.add(page.getPageNum()+i);
                }
            }
            model.addAttribute("pageMax",page.getPages());
            model.addAttribute("questions",questions);
            model.addAttribute("pages",arrayList);
            model.addAttribute("page",page.getPageNum());
            model.addAttribute("section","question");
            model.addAttribute("sectionName","我的提问");
        }else if ("replies".equals(action)){
            model.addAttribute("section","replies");
            model.addAttribute("sectionName","最新回复");
        }
        return "profile";

    }
}
