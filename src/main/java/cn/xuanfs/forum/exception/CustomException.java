package cn.xuanfs.forum.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * 自定义异常回复
 * @author xzj
 */
public class CustomException extends RuntimeException{

    private Status message;

    public CustomException(Status message) {

        this.message = message;
    }

    @Override
    public String getMessage(){
        return message.getMessage();
    }


    public enum Status{

        SubCommentError(400,"您评论的回复不存在，要不换个评论试试？"),
        QuestionError(400,"您查找的问题不在，要不换个问题试试？"),
        LoginError(1001,"请先登录"),
        CommentError(1002,"评论失败，请稍后再试"),
        CommentSuccess(200,"评论成功");

        @Getter
        @Setter
        private int code;
        @Getter
        @Setter
        private String message;


        Status(int code,String message){
            this.code = code;
            this.message = message;
        }
    }
}

