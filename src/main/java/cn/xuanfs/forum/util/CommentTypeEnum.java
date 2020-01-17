package cn.xuanfs.forum.util;

import lombok.Getter;
import lombok.Setter;

/**
 * @author xzj
 */
public enum CommentTypeEnum {

    SUB_COMMENT(2),
    COMMENT(1);

    @Getter
    @Setter
    private int type;
    CommentTypeEnum(int type){
        this.type = type;
    }
}
