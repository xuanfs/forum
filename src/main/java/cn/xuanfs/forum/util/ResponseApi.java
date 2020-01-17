package cn.xuanfs.forum.util;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class ResponseApi {

    private int code;
    private String msg;
    private Object date;

    public ResponseApi(int code, String msg, Object date) {
        this.code = code;
        this.msg = msg;
        this.date = date;
    }

    public  ResponseApi(){
    }

    public static ResponseApi ofOk(Object date){
        return new ResponseApi(Status.SUCCESS.getCode(),Status.SUCCESS.getMsg(),date);
    }

    public static ResponseApi ofError(){
        return new ResponseApi(Status.FAILED.getCode(),Status.FAILED.getMsg(),null);
    }

    public enum Status{
        FAILED(400,"请求失败"),
        SUCCESS(200,"请求成功");

        @Getter
        @Setter
        private int code;
        @Getter
        @Setter
        private String msg;

        Status(int code,String msg){
            this.code = code;
            this.msg = msg;
        }
    }
}
