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

        QuestionError("您查找的问题不在，要不换个问题试试？？？");

        @Getter
        @Setter
        private String message;

        Status(String message){
            this.message = message;
        }
    }
}

