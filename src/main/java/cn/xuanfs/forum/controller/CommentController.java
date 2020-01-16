package cn.xuanfs.forum.controller;

import cn.xuanfs.forum.entity.Comment;
import cn.xuanfs.forum.entity.Question;
import cn.xuanfs.forum.entity.User;
import cn.xuanfs.forum.exception.CustomException;
import cn.xuanfs.forum.mapper.CommentMapper;
import cn.xuanfs.forum.mapper.QuestionMapper;
import cn.xuanfs.forum.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xzj
 */
@Controller
public class CommentController {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private QuestionMapper questionMapper;

    private Map<String,Object> map = new HashMap<>();


    @ResponseBody
    @GetMapping("/comment")
    private Map<String,Object> post(Comment comment,HttpServletRequest request){
        System.out.println("comment:"+comment);

        map.clear();

        User user = (User) request.getSession().getAttribute("user");
        if(user == null){
            map.put("code",CustomException.Status.LoginError.getCode());
            map.put("msg",CustomException.Status.LoginError.getMessage());
            return map;
        }
        Question question = questionMapper.findById(comment.getParentId());
        if(question == null){
            map.put("msg",CustomException.Status.QuestionError.getMessage());
            return map;
        }

        Date date = new Date();
        comment.setGmtModified(date);
        comment.setGmtCreate(date);
        comment.setCommentator(user.getId());
        questionMapper.updateCommentById(question);
        int insert = commentMapper.insert(comment);
        if(insert<=0){
            map.put("code",CustomException.Status.CommentError.getCode());
            map.put("msg",CustomException.Status.CommentError.getMessage());
            return map;
        }
        map.put("code",CustomException.Status.CommentSuccess.getCode());
        map.put("msg",CustomException.Status.CommentSuccess.getMessage());
        return map;
    }

}
