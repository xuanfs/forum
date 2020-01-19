package cn.xuanfs.forum.util;

import lombok.Getter;

/**
 * @author xzj
 */
public enum NotificationStatusEnum {
    UNREAD(0),READ(1)
    ;
    @Getter
    private int status;

    NotificationStatusEnum(int status) {
        this.status = status;
    }
}
