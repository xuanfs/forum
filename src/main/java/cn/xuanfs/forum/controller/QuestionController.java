package cn.xuanfs.forum.controller;

import cn.xuanfs.forum.entity.Question;
import cn.xuanfs.forum.mapper.QuestionMapper;
import cn.xuanfs.forum.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author xzj
 */
@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id")Long id,
                           Model model){
        Question question = questionService.findById(id);
        model.addAttribute("question",question);
        return "question";
    }
}
