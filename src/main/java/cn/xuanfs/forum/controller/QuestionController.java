package cn.xuanfs.forum.controller;

import cn.xuanfs.forum.entity.Comment;
import cn.xuanfs.forum.entity.Question;
import cn.xuanfs.forum.service.CommentService;
import cn.xuanfs.forum.service.QuestionService;
import cn.xuanfs.forum.util.CommentTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @author xzj
 */
@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private CommentService commentService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id")Long id,
                           Model model){
        Question question = questionService.findById(id);
        List<Comment> comments = commentService.findCommentByParentId(CommentTypeEnum.COMMENT.getType(),id);
        model.addAttribute("question",question);
        model.addAttribute("comments",comments);
        return "question";
    }
}
