package cn.xuanfs.forum.mapper;

import cn.xuanfs.forum.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * @author xzj
 */
public interface UserMapper {

    void insert(User user);

    User findById(String id);

    User login(Map map);

    User findByAccoutId(String accoutId);

    void update(User user);

    void headImage(@Param("headImage")String headImage,@Param("id")String id);
}
