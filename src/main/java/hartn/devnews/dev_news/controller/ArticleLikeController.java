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
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.ResponseStatus;

import hartn.devnews.dev_news.controller.exception.ResourceNotFoundException;
import hartn.devnews.dev_news.model.Article;
import hartn.devnews.dev_news.model.ArticleLike;
import hartn.devnews.dev_news.repository.ArticleRepository;
import hartn.devnews.dev_news.repository.ArticleLikeRepository;

@RequestMapping("/articles")
@Controller
public class ArticleLikeController {

    ArticleRepository articleRepository;
    ArticleLikeRepository articleLikeRepository;

    public ArticleLikeController(ArticleRepository articleRepository, ArticleLikeRepository articleLikeRepository) {
        this.articleRepository = articleRepository;
        this.articleLikeRepository = articleLikeRepository;
    }

    // Return likes on given article

    @GetMapping("/{articleId}/likes")
    public ResponseEntity<List<ArticleLike>> getArticleLikes(@PathVariable Long articleId) {
        Article article = articleRepository.findById(articleId).orElseThrow(ResourceNotFoundException::new);
        List<ArticleLike> articleLikes = article.getArticleLikes();
        return ResponseEntity.ok(articleLikes);
    }

    // Create like on given article

    @PostMapping("/{articleId}/likes")
    public ResponseEntity<ArticleLike> createLikeArticle(@PathVariable Long articleId, @RequestBody ArticleLike like) {
        Article article = articleRepository.findById(articleId).orElseThrow(ResourceNotFoundException::new);
        like.setArticleLike(article);
        articleLikeRepository.save(like);
        return ResponseEntity.status(HttpStatus.CREATED).body(like);
    }

    // Update given like

    @PutMapping("/likes/{id}")
    public ResponseEntity<ArticleLike> updateArticleLike(@PathVariable Long id,
            @RequestBody ArticleLike updatedArticleLike) {
        ArticleLike fetchedArticleLike = articleLikeRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        fetchedArticleLike.setLikeBy(updatedArticleLike.getLikeBy());

        articleLikeRepository.save(fetchedArticleLike);
        return ResponseEntity.ok(fetchedArticleLike);
    }

    // Delete given like.

    @DeleteMapping("/likes/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteArticleLike(@PathVariable Long id) {
        ArticleLike articleLike = articleLikeRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        articleLikeRepository.delete(articleLike);
    }

}
