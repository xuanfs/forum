package cn.xuanfs.forum.service;

import cn.xuanfs.forum.entity.Comment;
import cn.xuanfs.forum.entity.Notification;
import cn.xuanfs.forum.mapper.NotificationMapper;
import cn.xuanfs.forum.util.NotificationEnum;
import cn.xuanfs.forum.util.NotificationStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author xzj
 */
@Service
public class NotificationService {

    @Autowired
    private NotificationMapper notificationMapper;

    private Notification notification = new Notification();

    public void createNotify(Comment comment, int receiver, NotificationEnum notificationEnum, Date date) {
        if(receiver == comment.getCommentator()){
            return;
        }
        notification.setGmtCreate(date);
        notification.setType(notificationEnum.getType());
        notification.setOuterId(comment.getParentId().intValue());
        notification.setNotifier(comment.getCommentator());
        notification.setStatus(NotificationStatusEnum.UNREAD.getStatus());
        notification.setReceiver(receiver);
        notificationMapper.insert(notification);
    }

    public int getUnread(int receiver){
        List<Notification> newNotifyByReceiver = findNewNotifyByReceiver(receiver);
        List<Notification> collect = newNotifyByReceiver.stream().filter(notification ->
                notification.getStatus() == 0
        ).collect(Collectors.toList());
        return collect.size();
    }

    public List<Notification> findNewNotifyByReceiver(int receiver){
        return notificationMapper.findNewNotifyByReceiver(receiver);
    }

    public void read(int id) {
        notificationMapper.read(id);
    }

    public Notification findNotifyById(int id){
        return notificationMapper.findNotifyById(id);
    }
}
