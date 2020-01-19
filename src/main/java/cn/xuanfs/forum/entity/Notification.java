package cn.xuanfs.forum.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author xzj
 */
@Data
public class Notification {
    private int id;
    private int notifier;
    private int receiver;
    private int outerId;
    private int type;
    private Date gmtCreate;
    private int status;

    private String notifierName;
    private String notifierTitle;
}
