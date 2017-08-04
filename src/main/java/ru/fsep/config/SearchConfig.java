package ru.fsep.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import ru.fsep.repositories.CommentDao;
import ru.fsep.repositories.impl.CommentSearchDaoImpl;

/**
 * 17.07.2017
 *
 * @author Robert Bagramov.
 */

@Configuration
public class SearchConfig {

    @Bean
    public CommentDao searchImpl() {
        return new CommentSearchDaoImpl();
    }
}

