package com.example.Day04Blog.controller;

import com.example.Day04Blog.entity.Article;
import com.example.Day04Blog.entity.CustomUserDetails;
import com.example.Day04Blog.entity.User;
import com.example.Day04Blog.repository.ArticleRepository;
import com.example.Day04Blog.repository.CustomArticleRepository;
import com.example.Day04Blog.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class ArticleController {
    @Autowired
    private ArticleRepository articleRepos;

    @Autowired
    private CustomArticleRepository customArticleRepos;

    @Autowired
    private UserRepository userRepos;

    @GetMapping({"", "/index"})
    public ModelAndView viewHomePage() {
        ModelAndView mav = new ModelAndView("index");
        List<Article> latestArticles = customArticleRepos.findTop5ByOrderByCreationTime();
        mav.addObject("latestArticles", latestArticles);
        return mav;
    }

    @GetMapping("/articleadd")
    ModelAndView createArticle(){
        ModelAndView mav = new ModelAndView("new-article");
        Article newArticle = new Article();
        mav.addObject("article", newArticle);
        return mav;
    }

    @PostMapping("/articleadd")
    String saveArticle(@Valid @ModelAttribute Article article, BindingResult result){
        if (result.hasErrors()){
            return "new-article";
        }

        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDetails.getUser();

        if (article.getId() == null) {
            if (user != null) {
                article.setUser(user);
            } else {
                // FIXME: throw exception / internal error
            }

            article.setCreationTime(LocalDateTime.now());
        }

        articleRepos.save(article);
        return "redirect:/index";
    }

    @GetMapping("/article")
    public ModelAndView loadArticle(@RequestParam Long id){
        ModelAndView mav = new ModelAndView("article");
        Article article = articleRepos.findArticleById(id);
        mav.addObject("article", article);
        return mav;
    }

    @GetMapping("/list-articles")
    public ModelAndView getArticleList(){
        ModelAndView mav = new ModelAndView("list-articles");
        List<Article> articles = articleRepos.findAllArticles();
        mav.addObject("articles", articles);
        return mav;
    }

    @GetMapping("/updateArticle")
    public ModelAndView updateArticle(@RequestParam Long id){
        ModelAndView mav = new ModelAndView("new-article");
        Article article = articleRepos.findArticleById(id);
        mav.addObject("article", article);
        return mav;
    }

    @GetMapping("/deleteArticle")
    public String deleteArticle(@RequestParam Long id){
        articleRepos.deleteById(id);
        return "redirect:/list-articles";
    }

}
