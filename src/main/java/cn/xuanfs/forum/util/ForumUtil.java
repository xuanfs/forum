package cn.xuanfs.forum.util;

import cn.xuanfs.forum.entity.Question;
import cn.xuanfs.forum.entity.User;
import cn.xuanfs.forum.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author xzj
 */
@Component
public class ForumUtil {

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

    //获取cookie中的id
    public String getCookieId(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        String id = null;
        if(cookies != null){
            for (Cookie cookie:cookies) {
                if("id".equals(cookie.getName())){
                    id = cookie.getValue();
                }
            }
        }
        return id;
    }
}
