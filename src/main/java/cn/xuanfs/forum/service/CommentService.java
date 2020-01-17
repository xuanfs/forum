package cn.xuanfs.forum.service;

import cn.xuanfs.forum.entity.Comment;
import cn.xuanfs.forum.mapper.CommentMapper;
import cn.xuanfs.forum.util.ForumUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xzj
 */
@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private ForumUtil forumUtil;

    public List<Comment> findCommentByParentId(int type,Long id) {
        List<Comment> lists = commentMapper.findCommentByParentId(type,id);
        return forumUtil.commentsAddUser(lists);
    }

    public int insert(Comment comment) {
        return commentMapper.insert(comment);
    }

    public Comment findCommentById(Long parentId) {
        return commentMapper.findCommentById(parentId);
    }

    public void updateCommentCount(int id) {
        commentMapper.updateCommentCount(id);
    }
}
