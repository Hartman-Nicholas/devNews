package hartn.devnews.dev_news.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import hartn.devnews.dev_news.model.ArticleCommentLike;

public interface ArticleCommentLikeRepository extends JpaRepository<ArticleCommentLike, Long> {

}
