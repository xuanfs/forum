package cn.xuanfs.forum.util;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author xzj
 */
@Data
public class UploadResponseApi {

    private int success;
    private String message;
    private String url;

    public UploadResponseApi(int success, String message, String url) {
        this.success = success;
        this.message = message;
        this.url = url;
    }

    public  UploadResponseApi(){
    }

    public static UploadResponseApi ofOk(String url){
        return new UploadResponseApi(Status.SUCCESS.getSuccess(),Status.SUCCESS.getMessage(),url);
    }

    public static UploadResponseApi ofError(){
        return new UploadResponseApi(Status.FAILED.getSuccess(),Status.FAILED.getMessage(),null);
    }

    public enum Status{
        FAILED(0,"图片上传失败"),
        SUCCESS(1,"图片上传成功");

        @Getter
        @Setter
        private int success;
        @Getter
        @Setter
        private String message;

        Status(int success,String message){
            this.success = success;
            this.message = message;
        }
    }
}
