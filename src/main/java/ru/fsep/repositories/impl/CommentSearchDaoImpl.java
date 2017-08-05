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

    //language=SQL
    private final String SQL_SELECT_COMMENTS_BY_SEARCH_QUERY_BY_SIMILARITY =
            "SELECT id, text, secondsfromstart " +
                    " FROM comment" +
                    " WHERE comment.text % :searchQuery";

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
        String newSearchComment = "";

        String delims = "[ .,?!]+";
        String[] tokens = searchComment.split(delims);

        for (String token : tokens) {
            newSearchComment += commentRepository.getCorrectedWord("'" + token + "'") + " ";
        }

        List<Comment> comments = this.getComments("'" + newSearchComment + "'");

        if (!comments.isEmpty()) {
            return comments;
        } else {
            return entityManager.createNativeQuery(SQL_SELECT_COMMENTS_BY_SEARCH_QUERY_BY_SIMILARITY, Comment.class)
                    .setParameter("searchQuery", newSearchComment)
                    .getResultList();
        }
    }
}
