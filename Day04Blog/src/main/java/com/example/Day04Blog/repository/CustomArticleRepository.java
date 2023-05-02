package com.example.Day04Blog.repository;

import com.example.Day04Blog.entity.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import jakarta.persistence.EntityManager;
import java.util.List;

@Repository
public class CustomArticleRepository {
    @Autowired
    private EntityManager entityManager;

    public List<Article> findTop5ByOrderByCreationTime() {
        return entityManager.createQuery("SELECT a FROM Article a LEFT JOIN FETCH a.user ORDER BY a.creationTime DESC",
                Article.class).setMaxResults(5).getResultList();
    }
}

