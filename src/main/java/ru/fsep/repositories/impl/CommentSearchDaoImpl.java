package ru.fsep.repositories.impl;

import org.springframework.beans.factory.annotation.Autowired;
import ru.fsep.models.Comment;
import ru.fsep.repositories.CommentDao;
import ru.fsep.repositories.CommentRepository;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * 17.07.2017
 *
 * @author Robert Bagramov.
 */
public class CommentSearchDaoImpl implements CommentDao {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private EntityManager entityManager;

    //language=SQL
    private final String SQL_SELECT_COMMENTS_BY_SEARCH_QUERY
            = "SELECT id, text, secondsfromstart " +
            "FROM comment, plainto_tsquery('ru', :searchQuery) query " +
            "WHERE fts @@ query OR comment.text ~* :searchQuery2";


    @Override
    public List<Comment> getComments(String searchComment) {
        List<Comment> list = entityManager.createNativeQuery(SQL_SELECT_COMMENTS_BY_SEARCH_QUERY, Comment.class)
                .setParameter("searchQuery", "'" + searchComment + "'")
                .setParameter("searchQuery2", ".*" + searchComment + ".*")
                .getResultList();
        return list;
    }

    @Override
    public List<Comment> getCommentsBySimilarity(String searchComment) {
        return commentRepository.getCommentsBySimilarity(searchComment);
    }
}
