package cn.xuanfs.forum.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author xzj
 */
@Data
public class Question {
    private Integer id;
    private String title;
    private String description;
    private String tag;
    private Date gmtCreate;
    private Date gmtModified;
    private Integer creator;
    private Integer viewCount;
    private Integer commentCount;
    private Integer likeCount;
}
