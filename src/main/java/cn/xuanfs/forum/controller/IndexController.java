package cn.xuanfs.forum.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 主页控制
 * @author xzj
 */
@Controller
public class IndexController {

    @GetMapping("/")
    public String index(){
        return "index";
    }

}
