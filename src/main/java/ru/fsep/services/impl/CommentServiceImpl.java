package ru.fsep.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.fsep.models.Comment;
import ru.fsep.repositories.CommentDao;
import ru.fsep.services.CommentService;

import java.util.List;

/**
 * 12.07.2017
 *
 * @author Robert Bagramov.
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentDao commentDao;

    @Override
    public List<Comment> getComments(String searchComment) {
        return commentDao.getComments(searchComment);
    }

    @Override
    public List<Comment> getCommentsBySimilarity(String searchComment) {
        return commentDao.getCommentsBySimilarity(searchComment);
    }
}
