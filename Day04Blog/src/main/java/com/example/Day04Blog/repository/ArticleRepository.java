package com.example.Day04Blog.repository;

import com.example.Day04Blog.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
public interface ArticleRepository extends JpaRepository<Article, Long> {

    @Query("SELECT a FROM Article a LEFT JOIN FETCH a.user where a.id = ?1")
    public Article findArticleById(Long id);

    @Query("SELECT a FROM Article a LEFT JOIN FETCH a.user ORDER BY a.id DESC")
    public List<Article> findAllArticles();

}
