package cn.xuanfs.forum.mapper;

import cn.xuanfs.forum.entity.Comment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author xzj
 */
public interface CommentMapper {
    int insert(Comment comment);

    List<Comment> findCommentByParentId(@Param("type")int type,@Param("parentId") Long ParentId);

    Comment findCommentById(Long parentId);

    void updateCommentCount(int id);
}
