package cn.xuanfs.forum.controller;

import cn.xuanfs.forum.entity.Comment;
import cn.xuanfs.forum.entity.Question;
import cn.xuanfs.forum.entity.User;
import cn.xuanfs.forum.exception.CustomException;
import cn.xuanfs.forum.mapper.QuestionMapper;
import cn.xuanfs.forum.service.CommentService;
import cn.xuanfs.forum.util.CommentTypeEnum;
import cn.xuanfs.forum.util.ResponseApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xzj
 */
@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private QuestionMapper questionMapper;

    private Map<String,Object> map = new HashMap<>();


    @ResponseBody
    @GetMapping("/comment")
    private Map<String,Object> post(Comment comment, HttpServletRequest request,
                                    Model model){
        map.clear();
        System.out.println(comment);
        User user = (User) request.getSession().getAttribute("user");
        if(user == null){
            map.put("code",CustomException.Status.LoginError.getCode());
            map.put("msg",CustomException.Status.LoginError.getMessage());
            return map;
        }
        //判断一二级评论
        if(comment.getType()==CommentTypeEnum.COMMENT.getType()){
            Question question = questionMapper.findById(comment.getParentId());
            if(question == null){
                map.put("msg",CustomException.Status.QuestionError.getMessage());
                return map;
            }
            questionMapper.updateCommentById(question);
        }else{
            Comment isComment = commentService.findCommentById(comment.getParentId());
            if (isComment == null){
                map.put("msg",CustomException.Status.SubCommentError.getMessage());
                return map;
            }
            commentService.updateCommentCount(comment.getParentId().intValue());
        }


        Date date = new Date();
        comment.setGmtModified(date);
        comment.setGmtCreate(date);
        comment.setCommentator(user.getId());

        int insert = commentService.insert(comment);
        if(insert<=0){
            map.put("code",CustomException.Status.CommentError.getCode());
            map.put("msg",CustomException.Status.CommentError.getMessage());
            return map;
        }
        map.put("code",CustomException.Status.CommentSuccess.getCode());
        map.put("msg",CustomException.Status.CommentSuccess.getMessage());
        return map;
    }

    @ResponseBody
    @GetMapping("/subComment/{id}")
    public ResponseApi question(@PathVariable(name = "id")Long id){
        List<Comment> subComment = commentService.findCommentByParentId(CommentTypeEnum.SUB_COMMENT.getType(), id);
        return ResponseApi.ofOk(subComment);
    }

}
