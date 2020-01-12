package cn.xuanfs.forum.entity;

import lombok.Data;

/**
 * @author xzj
 */
@Data
public class AccessToken {
    private String client_id;
    private String client_secret;
    private String code;
    private String redirectUri;
    private String state;
}
