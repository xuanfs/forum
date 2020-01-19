package cn.xuanfs.forum.util;

import cn.xuanfs.forum.entity.Comment;
import cn.xuanfs.forum.entity.Notification;
import cn.xuanfs.forum.entity.Question;
import cn.xuanfs.forum.entity.User;
import cn.xuanfs.forum.mapper.CommentMapper;
import cn.xuanfs.forum.mapper.QuestionMapper;
import cn.xuanfs.forum.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private CommentMapper commentMapper;

    List<Integer> userIds = new ArrayList();
    List<User> userList = new ArrayList<>();

    //完善通知
    public List<Notification> notificationComplete(List<Notification> list){
        if(list.size() != 0){
            userIds.clear();
            userList.clear();
            //获取评论用户和评论问题（去重）
            Set<Integer> notifiers = list.stream().map(notification -> notification.getNotifier()).collect(Collectors.toSet());
            userIds.addAll(notifiers);

            //将评论用户和问题转换为map
            Object[] userArray = userIds.toArray();
            for (int i = 0; i < userArray.length; i++) {
                User user = userMapper.findById(String.valueOf(userArray[i]));
                userList.add(user);
            }
            Map<Integer, String> userMap = userList.stream().collect(Collectors.toMap(user -> user.getId(), user -> user.getName()));

            //评论插入用户
            List<Notification> notificationList = list.stream().map(notification -> {
                notification.setNotifierName(userMap.get(notification.getNotifier()));
                return notification;
            }).collect(Collectors.toList());
            for (int i = 0; i < notificationList.size(); i++) {
                if(notificationList.get(i).getType()==1){
                    Question question = questionMapper.findById(Long.valueOf(notificationList.get(i).getOuterId()));
                    notificationList.get(i).setNotifierTitle(question.getTitle());
                }else{
                    Comment comment = commentMapper.findComment(notificationList.get(i).getOuterId());
                    notificationList.get(i).setNotifierTitle(comment.getContent());
                }
            }
            return notificationList;
        }
        return list;
    }

    //评论列表添加用户
    public List<Comment> commentsAddUser(List<Comment> list){

        if(list.size() != 0){
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
}
