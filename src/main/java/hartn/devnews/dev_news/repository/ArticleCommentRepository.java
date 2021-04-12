package hartn.devnews.dev_news.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import hartn.devnews.dev_news.classes.ArticleComment;

public interface ArticleCommentRepository extends JpaRepository<ArticleComment, Long> {

    List<ArticleComment> findByAuthorName(String authorName);

}
