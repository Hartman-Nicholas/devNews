package hartn.devnews.dev_news.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import hartn.devnews.dev_news.classes.ArticleComment;

public interface ArticleCommentRepository extends JpaRepository<ArticleComment, Long> {

    // @Query(value = "SELECT * FROM ARTICLECOMMENT WHERE articleOwner.id = ?1",
    // nativeQuery = true)
    // List<ArticleComment> findByArticleOwner(Long articleOwner);

    List<ArticleComment> findByAuthorName(String authorName);

}
