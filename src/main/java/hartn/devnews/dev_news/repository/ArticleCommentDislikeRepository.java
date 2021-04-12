package hartn.devnews.dev_news.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import hartn.devnews.dev_news.classes.ArticleCommentDislike;

public interface ArticleCommentDislikeRepository extends JpaRepository<ArticleCommentDislike, Long> {

}
