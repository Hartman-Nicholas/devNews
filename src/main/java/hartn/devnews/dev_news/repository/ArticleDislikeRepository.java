package hartn.devnews.dev_news.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import hartn.devnews.dev_news.model.ArticleDislike;

public interface ArticleDislikeRepository extends JpaRepository<ArticleDislike, Long> {

}
