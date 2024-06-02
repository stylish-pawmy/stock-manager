package org.example.gestionstock.Controllers;

import org.example.gestionstock.Models.Article;
import org.example.gestionstock.Repositories.ArticleRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
        UserDetails userDetails = (UserDetails) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();

        page.addAttribute("principal", userDetails);
        page.addAttribute("principalRole", userDetails
                .getAuthorities().stream().findFirst().get().getAuthority());

        var articles = articleRepository.findAll();
        page.addAttribute("articles", articles);

        return "article/articles";
    }

    @PreAuthorize("hasRole('MAGASIN')")
    @GetMapping("/ajouter-article")
    public String getAjouterArticle(Model page)
    {
        UserDetails userDetails = (UserDetails) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();

        page.addAttribute("principal", userDetails);
        page.addAttribute("principalRole", userDetails
                .getAuthorities().stream().findFirst().get().getAuthority());

        Article article = new Article();
        page.addAttribute("article", article);

        return "article/ajouter-article";
    }

    @PreAuthorize("hasRole('MAGASIN')")
    @GetMapping("/modifier-article")
    public String getModifierArticle(
            Model page,
            @RequestParam("article_id") int id
    )
    {
        UserDetails userDetails = (UserDetails) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();

        page.addAttribute("principal", userDetails);
        page.addAttribute("principalRole", userDetails
                .getAuthorities().stream().findFirst().get().getAuthority());

        Article article = articleRepository.findById(id).orElse(null);
        page.addAttribute("article", article);

        return "article/ajouter-article";
    }

    @PreAuthorize("hasRole('MAGASIN')")
    @PostMapping("/ajouter-article")
    public String postAjouterArticle(@ModelAttribute Article article, Model page)
    {
        articleRepository.save(article);

        UserDetails userDetails = (UserDetails) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();

        page.addAttribute("principal", userDetails);
        page.addAttribute("principalRole", userDetails
                .getAuthorities().stream().findFirst().get().getAuthority());

        page.addAttribute("articles", articleRepository.findAll());
        return "article/articles";
    }

    @PreAuthorize("hasRole('MAGASIN')")
    @GetMapping("/supprimer-article")
    public String deleteArticle(@RequestParam("article_id") int id, Model page)
    {
        articleRepository.deleteById(id);
        page.addAttribute("articles", articleRepository.findAll());

        UserDetails userDetails = (UserDetails) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();

        page.addAttribute("principal", userDetails);
        page.addAttribute("principalRole", userDetails
                .getAuthorities().stream().findFirst().get().getAuthority());

        return "article/articles";
    }

}
