package cn.xuanfs.forum.service;

import cn.xuanfs.forum.entity.Question;
import cn.xuanfs.forum.entity.User;
import cn.xuanfs.forum.mapper.QuestionMapper;
import cn.xuanfs.forum.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xzj
 */
@Service
public class QuestionService {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    //问题列表添加用户
    public List<Question> listAddUser(List<Question> list){
        if(list.size()!=0){
            for (Question question:list) {
                User user = userMapper.findById(String.valueOf(question.getCreator()));
                question.setUser(user);
            }
            return list;
        }
        return null;
    }

    public List<Question> list(){
        List<Question> list = questionMapper.list();
        return listAddUser(list);
    }

    public List<Question> listByCreator(String creator){
        List<Question> list = questionMapper.listByCreator(creator);
        return listAddUser(list);
    }
}
