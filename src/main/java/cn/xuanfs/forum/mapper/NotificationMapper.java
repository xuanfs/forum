package cn.xuanfs.forum.mapper;

import cn.xuanfs.forum.entity.Notification;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NotificationMapper {
    void insert(Notification notification);

    List<Notification> findNewNotifyByReceiver(@Param("receiver")int receiver);

    void read(int id);

    Notification findNotifyById(int id);
}
