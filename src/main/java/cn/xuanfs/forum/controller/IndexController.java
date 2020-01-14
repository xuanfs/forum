package cn.xuanfs.forum.controller;

import cn.xuanfs.forum.entity.Question;
import cn.xuanfs.forum.entity.User;
import cn.xuanfs.forum.mapper.UserMapper;
import cn.xuanfs.forum.service.QuestionService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.naming.Name;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
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

    private ArrayList arrayList = new ArrayList();

    @GetMapping("/")
    public String index(HttpServletRequest request, Model model,@RequestParam(name = "pageNumber",required = false,defaultValue = "1") int pageNumber){
        Cookie[] cookies = request.getCookies();
        if(cookies != null){
            for (Cookie cookie:cookies) {
                if("id".equals(cookie.getName())){
                    String id = cookie.getValue();
                    User user = userMapper.findById(id);
                    if(user != null){
                        request.getSession().setAttribute("user",user);
                        request.getSession().setAttribute("username",user.getName());
                    }
                    break;
                }
            }
        }
        //pageSize:2	pages:6	pageNum:4	pageTotal:11
        /**
         *  1
         *         1 2 3
         *  2
         *       1 2 3 4
         *  3
         *     1 2 3 4 5
         *  4
         *     2 3 4 5 6
         *  5
         *     3 4 5 6
         *  6
         *     4 5 6
         *  如果当前页小于等于 1+2
         *  如果当前页大于等于 pages-2
         *
         */
        Page page = PageHelper.startPage(pageNumber, 5);
        List<Question> questions = questionService.list();
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
        return "index";
    }

}
