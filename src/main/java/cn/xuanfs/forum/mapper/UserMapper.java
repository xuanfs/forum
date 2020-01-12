package cn.xuanfs.forum.mapper;

import cn.xuanfs.forum.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author xzj
 */
public interface UserMapper {

    void insert(User user);
}
