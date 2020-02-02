package cn.xuanfs.forum.controller;

import cn.xuanfs.forum.entity.User;
import cn.xuanfs.forum.mapper.UserMapper;
import cn.xuanfs.forum.util.UcloudImageUtil;
import cn.xuanfs.forum.util.UploadResponseApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author xzj
 */
@Controller
public class imageUploadController {

    @Autowired
    private UcloudImageUtil ucloudImageUtil;

    @Autowired
    private UserMapper userMapper;

    @Value("${ucloud.image.headPath}")
    private StringBuffer headPath;

    @RequestMapping("/image/upload")
    @ResponseBody
    public UploadResponseApi uploadImage(HttpServletRequest request){
        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest)request;
        MultipartFile file = multipartHttpServletRequest.getFile("editormd-image-file");
        try {
            String upload = ucloudImageUtil.upload(file.getInputStream(), file.getContentType(), file.getOriginalFilename());
            headPath.delete(32,headPath.length());
            headPath.append(upload);
            UploadResponseApi uploadResponseApi = UploadResponseApi.ofOk(headPath.toString());
            System.out.println("uploadResponseApi:"+uploadResponseApi);
            return uploadResponseApi;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    @RequestMapping("/avatarUrl")
    public String modifiedAvatarUrl(HttpServletRequest request){
        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest)request;
        MultipartFile file = multipartHttpServletRequest.getFile("headImage");

        try {
            String upload = ucloudImageUtil.upload(file.getInputStream(), file.getContentType(), file.getOriginalFilename());
            headPath.delete(32,headPath.length());
            headPath.append(upload);
            User user = (User) request.getSession().getAttribute("user");
            user.setAvatarUrl(headPath.toString());
            userMapper.headImage(headPath.toString(),user.getId().toString());
            request.getSession().setAttribute("user",user);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "user";
    }
}
