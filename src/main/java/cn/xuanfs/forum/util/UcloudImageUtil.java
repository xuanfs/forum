package cn.xuanfs.forum.util;

import cn.ucloud.ufile.UfileClient;
import cn.ucloud.ufile.api.object.ObjectConfig;
import cn.ucloud.ufile.auth.ObjectAuthorization;
import cn.ucloud.ufile.auth.UfileObjectLocalAuthorization;
import cn.ucloud.ufile.bean.PutObjectResultBean;
import cn.ucloud.ufile.exception.UfileClientException;
import cn.ucloud.ufile.exception.UfileServerException;
import cn.ucloud.ufile.http.OnProgressListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.InputStream;
import java.util.UUID;

/**
 * @author xzj
 */
@Component
public class UcloudImageUtil {

    @Value("${ucloud.image.public-key}")
    private String publicKey;

    @Value("${ucloud.image.private-key}")
    private String privateKey;

    @Value("${ucloud.image.bucket}")
    private String bucket;

    @Value("${ucloud.image.region}")
    private String region;

    private StringBuffer name = new StringBuffer();

    public String upload(InputStream inputStream,String mimeType,String fileName){
        // Bucket相关API的授权器
        ObjectAuthorization OBJECT_AUTHORIZER  = new UfileObjectLocalAuthorization(publicKey, privateKey);
        // 对象操作需要ObjectConfig来配置您的地区和域名后缀
        ObjectConfig config = new ObjectConfig(region, "ufileos.com");
        name.delete(0,name.length());
        name.append(UUID.randomUUID().toString() + fileName);


        try {
            PutObjectResultBean response = UfileClient.object(OBJECT_AUTHORIZER, config)
                    .putObject(inputStream, mimeType)
                    .nameAs(name.toString())
                    .toBucket(bucket)
                    /**
                     * 是否上传校验MD5, Default = true
                     */
                    //  .withVerifyMd5(false)
                    /**
                     * 指定progress callback的间隔, Default = 每秒回调
                     */
                    //  .withProgressConfig(ProgressConfig.callbackWithPercent(10))
                    /**
                     * 配置进度监听
                     */
                    .setOnProgressListener(new OnProgressListener() {
                        @Override
                        public void onProgress(long bytesWritten, long contentLength) {

                        }
                    })
                    .execute();
        } catch (UfileClientException e) {
            e.printStackTrace();
            return null;
        } catch (UfileServerException e) {
            e.printStackTrace();
            return null;
        }
        return name.toString();
    }
}
