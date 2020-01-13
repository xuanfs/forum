package cn.xuanfs.forum.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author xzj
 */
@Data
public class User {
    private Integer id;
    private String name;
    private String accoutId;
    private String token;
    private Date gmtCreate;
    private Date gmtModified;
    private String avatarUrl;
}
