package hartn.devnews.dev_news.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import hartn.devnews.dev_news.classes.ArticleLike;

public interface ArticleLikeRepository extends JpaRepository<ArticleLike, Long> {

}
