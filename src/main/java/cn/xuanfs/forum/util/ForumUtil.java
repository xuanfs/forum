package cn.xuanfs.forum.util;

import cn.xuanfs.forum.entity.Comment;
import cn.xuanfs.forum.entity.Question;
import cn.xuanfs.forum.entity.User;
import cn.xuanfs.forum.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author xzj
 */
@Component
public class ForumUtil {

    @Autowired
    private UserMapper userMapper;

    List<Integer> userIds = new ArrayList();
    List<User> userList = new ArrayList<>();

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

    //评论列表添加用户
    public List<Comment> commentsAddUser(List<Comment> list){

        if(list.size() != 0){
//            for (Comment comment:list) {
//                User user = userMapper.findById(String.valueOf(comment.getCommentator()));
//                comment.setUser(user);
//            }
//            return list;
            //获取评论用户（去重）
            userList.clear();
            userIds.clear();
            Set<Integer> commentators = list.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());
            userIds.addAll(commentators);

            //将评论用户转换为map
            Object[] objects = userIds.toArray();
            for (int i = 0; i < objects.length; i++) {
                User user = userMapper.findById(String.valueOf(objects[i]));
                userList.add(user);
            }
            Map<Integer, User> userMap = userList.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));

            //评论插入用户
            List<Comment> commentList = list.stream().map(comment -> {
                comment.setUser(userMap.get(comment.getCommentator()));
                return comment;
            }).collect(Collectors.toList());
            return commentList;
        }
        return list;
    }
}
