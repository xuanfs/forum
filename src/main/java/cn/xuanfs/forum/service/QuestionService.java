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

    public List<Question> hostQuestion(){
        return questionMapper.hostQuestion();
    }

    public List<Question> list(String search){
        List<Question> list = questionMapper.list(search);
        return forumUtil.listAddUser(list);
    }

    public List<Question> listByCreator(String creator){
        List<Question> list = questionMapper.listByCreator(creator);
        return forumUtil.listAddUser(list);
    }

    public Question findById(Long id) {
        Question question = questionMapper.findById(id);
        if(question == null){
            throw new CustomException(CustomException.Status.QuestionError);
        }
        User user = userMapper.findById(String.valueOf(question.getCreator()));
        question.setUser(user);
        questionMapper.updateView(question);
        return question;
    }

    public List<Question> findAlikeByTag(Question question) {
        if(question.getTag() != null || question.getTag() != ""){
            String tag = question.getTag().replace(",", "|");
            List<Question> alikeByTag = questionMapper.findAlikeByTag(question.getId(), tag);
            return alikeByTag;
        }
        return null;
    }
}
