package org.example.gestionstock.Controllers;

import org.example.gestionstock.Models.Article;
import org.example.gestionstock.Repositories.ArticleRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ArticleController {
    private final ArticleRepository articleRepository;

    public ArticleController(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @PreAuthorize("hasRole('MAGASIN')")
    @GetMapping("/articles")
    public String getArticles(Model page)
    {
        var articles = articleRepository.findAll();
        page.addAttribute("articles", articles);

        return "article/articles";
    }

    @PreAuthorize("hasRole('MAGASIN')")
    @GetMapping("/ajouter-article")
    public String getAjouterArticle(Model model)
    {
        Article article = new Article();
        model.addAttribute("article", article);

        return "article/ajouter-article";
    }

    @PreAuthorize("hasRole('MAGASIN')")
    @GetMapping("/modifier-article")
    public String getModifierArticle(
            Model model,
            @RequestParam("article_id") int id
    )
    {
        Article article = articleRepository.findById(id).orElse(null);
        model.addAttribute("article", article);

        return "article/ajouter-article";
    }

    @PreAuthorize("hasRole('MAGASIN')")
    @PostMapping("/ajouter-article")
    public String postAjouterArticle(@ModelAttribute Article article, Model page)
    {
        articleRepository.save(article);

        page.addAttribute("articles", articleRepository.findAll());
        return "article/articles";
    }

    @PreAuthorize("hasRole('MAGASIN')")
    @GetMapping("/supprimer-article")
    public String deleteArticle(@RequestParam("article_id") int id, Model page)
    {
        articleRepository.deleteById(id);
        page.addAttribute("articles", articleRepository.findAll());

        return "article/articles";
    }

}
