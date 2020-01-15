package cn.xuanfs.forum.service;

import cn.xuanfs.forum.entity.Question;
import cn.xuanfs.forum.entity.User;
import cn.xuanfs.forum.exception.CustomException;
import cn.xuanfs.forum.mapper.QuestionMapper;
import cn.xuanfs.forum.mapper.UserMapper;
import cn.xuanfs.forum.util.ForumUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xzj
 */
@Service
public class QuestionService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private ForumUtil forumUtil;



    public List<Question> list(){
        List<Question> list = questionMapper.list();
        return forumUtil.listAddUser(list);
    }

    public List<Question> listByCreator(String creator){
        List<Question> list = questionMapper.listByCreator(creator);
        return forumUtil.listAddUser(list);
    }

    public Question findById(Integer id) {
        Question question = questionMapper.findById(id);
        if(question == null){
            throw new CustomException(CustomException.Status.QuestionError);
        }
        User user = userMapper.findById(String.valueOf(question.getCreator()));
        question.setUser(user);
        questionMapper.updateView(question);
        return question;
    }
}
