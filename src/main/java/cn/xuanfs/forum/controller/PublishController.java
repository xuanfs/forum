package cn.xuanfs.forum.controller;

import cn.xuanfs.forum.entity.Question;
import cn.xuanfs.forum.entity.User;
import cn.xuanfs.forum.mapper.QuestionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @Autowired
    private QuestionMapper questionMapper;

    @GetMapping("/publish")
    public String toPublish(){
        return "publish";
    }

    @GetMapping("/publish/{id}")
    public String edit(@PathVariable(name = "id")Long id,
                       Model model){
        Question question = questionMapper.findById(id);
        model.addAttribute("title",question.getTitle());
        model.addAttribute("description",question.getDescription());
        model.addAttribute("tag",question.getTag());
        model.addAttribute("id",question.getId());
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(Question question, HttpServletRequest request, Model model){
        //判断标题和问题是否为空
        if(question.getTitle()==""||question.getDescription()==""){
            model.addAttribute("title",question.getTitle());
            model.addAttribute("description",question.getDescription());
            model.addAttribute("msg","标题或问题不能为空");
            return "publish";
        }

        //获取添加问题用户信息
        User user = (User) request.getSession().getAttribute("user");
        if(user == null){
            model.addAttribute("msg","用户未登陆");
            return "publish";
        }
        Date date = new Date();
        question.setGmtModified(date);
        //查看是否修改问题
        if(question.getId()!=null){
            Integer result = questionMapper.updateQuestion(question);
            System.out.println("result:"+result);
            if(result <= 0){
                model.addAttribute("msg","修改失败");
                return "publish";
            }
            model.addAttribute("msg","修改成功");
            return "publish";
        }
        question.setGmtCreate(date);
        question.setCreator(user.getId());
        questionMapper.create(question);
        model.addAttribute("msg","发布成功");
        return "publish";
    }
}
