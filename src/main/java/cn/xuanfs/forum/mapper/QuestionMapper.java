package cn.xuanfs.forum.mapper;

import cn.xuanfs.forum.entity.Question;

import java.util.List;

/**
 * @author xzj
 */
public interface QuestionMapper {

    void create(Question question);

    List<Question> list();

    List<Question> listByCreator(String creator);

    Question findById(Long id);

    Integer updateQuestion(Question question);

    void updateView(Question question);

    void updateCommentById(Question question);
}
