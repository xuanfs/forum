package cn.xuanfs.forum.util;

import com.github.pagehelper.Page;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 放回页数
 * @author xzj
 */
@Component
public class PageNumUtil {

    private List arrayList = new ArrayList();

    public List getPageNub(Page page){
        arrayList.clear();
        for (int i = 2; i > 0; i--) {
            if(page.getPageNum()-i >= 1){
                arrayList.add(page.getPageNum()-i);
            }
        }
        for(int i=0;i<3;i++){
            if(page.getPageNum()+i<=page.getPages()){
                arrayList.add(page.getPageNum()+i);
            }
        }
        return arrayList;
    }
}
