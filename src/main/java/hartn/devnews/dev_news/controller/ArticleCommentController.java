package hartn.devnews.dev_news.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import hartn.devnews.dev_news.classes.Article;
import hartn.devnews.dev_news.classes.ArticleComment;
import hartn.devnews.dev_news.exception.ResourceNotFoundException;
import hartn.devnews.dev_news.repository.ArticleCommentRepository;
import hartn.devnews.dev_news.repository.ArticleRepository;

@Controller
public class ArticleCommentController {

    ArticleRepository articleRepository;
    ArticleCommentRepository articleCommentRepository;

    public ArticleCommentController(ArticleRepository articleRepository,
            ArticleCommentRepository articleCommentRepository) {
        this.articleRepository = articleRepository;
        this.articleCommentRepository = articleCommentRepository;
    };

    @GetMapping("/articles/{articleId}/comments")
    public ResponseEntity<List<ArticleComment>> getArticleComments(@PathVariable Long articleId) {
        Article article = articleRepository.findById(articleId).orElseThrow(ResourceNotFoundException::new);
        List<ArticleComment> comments = article.getArticleComments();
        return ResponseEntity.ok(comments);
    }

    @GetMapping("/comments")
    public ResponseEntity<List<ArticleComment>> getArticleCommentsByAuthor(
            @RequestParam(value = "authorName", required = true) String authorName) {
        List<ArticleComment> articleComment = articleCommentRepository.findByAuthorName(authorName);
        return ResponseEntity.ok(articleComment);

    }

    @PostMapping("/articles/{articleId}/comments")
    public ResponseEntity<ArticleComment> createArticleComment(@PathVariable Long articleId,
            @RequestBody ArticleComment articleComment) {
        Article article = articleRepository.findById(articleId).orElseThrow(ResourceNotFoundException::new);
        articleComment.setArticleOwner(article);
        articleCommentRepository.save(articleComment);
        return ResponseEntity.status(HttpStatus.CREATED).body(articleComment);

    }

    @PutMapping("/comments/{id}")
    public ResponseEntity<ArticleComment> updateArticleComment(@PathVariable Long id,
            @RequestBody ArticleComment updatedArticleComment) {
        ArticleComment fetchedArticleComment = articleCommentRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
        updatedArticleComment = fetchedArticleComment.setUpdateArticleCommentValues(updatedArticleComment);

        updatedArticleComment.setId(id);
        articleCommentRepository.save(updatedArticleComment);
        return ResponseEntity.ok(updatedArticleComment);

    }

    @DeleteMapping("/comments/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteArticleComment(@PathVariable Long id) {
        ArticleComment articleComment = articleCommentRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
        articleCommentRepository.delete(articleComment);
    }

}
