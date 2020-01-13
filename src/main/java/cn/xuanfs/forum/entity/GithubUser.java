package cn.xuanfs.forum.entity;

import lombok.Data;

/**
 * github用户
 * @author xzj
 */
@Data
public class GithubUser {

    private Long id;
    private String name;
    private String bio;
    private String avatar_url;
}
