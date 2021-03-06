package hartn.devnews.dev_news.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import hartn.devnews.dev_news.model.Article;

public interface ArticleRepository extends JpaRepository<Article, Long> {

}
