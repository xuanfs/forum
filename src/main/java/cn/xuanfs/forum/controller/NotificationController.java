package cn.xuanfs.forum.controller;

import cn.xuanfs.forum.entity.Comment;
import cn.xuanfs.forum.entity.Notification;
import cn.xuanfs.forum.entity.User;
import cn.xuanfs.forum.exception.CustomException;
import cn.xuanfs.forum.service.CommentService;
import cn.xuanfs.forum.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author xzj
 */
@Controller
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private CommentService commentService;

    @GetMapping("/notification/{id}")
    public String notificationRead(@PathVariable(name = "id")int id,
                                   HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        if(user == null){
            return "redirect:/";
        }
        Notification notify = notificationService.findNotifyById(id);
        if(notify == null){
            throw new CustomException(CustomException.Status.NotifyNull);
        }
        int res = 0;
        if(notify.getType()==1){
            notificationService.read(id);
            res = notify.getOuterId();
        }else{
            notificationService.read(id);
            Comment comment = commentService.findComment(notify.getOuterId());
            res = comment.getParentId().intValue();
        }
        int unread = notificationService.getUnread(user.getId());
        request.getSession().setAttribute("notifyNub",unread);
        return "redirect:/question/"+res;
    }
}
