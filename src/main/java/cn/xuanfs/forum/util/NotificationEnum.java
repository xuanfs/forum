package cn.xuanfs.forum.util;

import lombok.Getter;

public enum NotificationEnum {
    REPLY_OUESTION(1,"回复了问题"),
    REPLY_COMMENT(2,"回复了评论")
    ;
    @Getter
    private int type;
    @Getter
    private String name;

    NotificationEnum(int type, String name) {
        this.type = type;
        this.name = name;
    }
}
