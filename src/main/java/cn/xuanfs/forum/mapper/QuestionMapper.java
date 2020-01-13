package cn.xuanfs.forum.mapper;

import cn.xuanfs.forum.entity.Question;

import java.util.List;

/**
 * @author xzj
 */
public interface QuestionMapper {

    void create(Question question);

    List<Question> list();
}
