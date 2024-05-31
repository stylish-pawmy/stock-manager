package org.example.gestionstock.Repositories;

import org.example.gestionstock.Models.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Integer> { }
