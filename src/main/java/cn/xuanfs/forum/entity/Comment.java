package cn.xuanfs.forum.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author xzj
 */
@Data
public class Comment {
    private int id;
    private Long parentId;
    private int type;
    private String content;
    private int commentator;
    private Date gmtCreate;
    private Date gmtModified;
    private Long likeCount;
    private int commentCount;
    private User user;

}
