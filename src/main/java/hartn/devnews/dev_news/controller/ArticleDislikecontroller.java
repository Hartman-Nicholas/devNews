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
import hartn.devnews.dev_news.model.ArticleDislike;
import hartn.devnews.dev_news.repository.ArticleDislikeRepository;
import hartn.devnews.dev_news.repository.ArticleRepository;

@RequestMapping("/articles")
@Controller
public class ArticleDislikecontroller {

    ArticleRepository articleRepository;
    ArticleDislikeRepository articleDislikeRepository;

    public ArticleDislikecontroller(ArticleRepository articleRepository,
            ArticleDislikeRepository articleDislikeRepository) {
        this.articleRepository = articleRepository;
        this.articleDislikeRepository = articleDislikeRepository;
    }

    // Return dislikes by given article

    @GetMapping("/{articleId}/dislikes")
    public ResponseEntity<List<ArticleDislike>> getArticleDislikes(@PathVariable Long articleId) {
        Article article = articleRepository.findById(articleId).orElseThrow(ResourceNotFoundException::new);
        List<ArticleDislike> articleDislikes = article.getArticleDislikes();
        return ResponseEntity.ok(articleDislikes);
    }

    // Cretae dislikes on given article

    @PostMapping("/{articleId}/dislikes")
    public ResponseEntity<ArticleDislike> createLikeArticle(@PathVariable Long articleId,
            @RequestBody ArticleDislike dislike) {
        Article article = articleRepository.findById(articleId).orElseThrow(ResourceNotFoundException::new);
        dislike.setArticleDislike(article);
        articleDislikeRepository.save(dislike);
        return ResponseEntity.status(HttpStatus.CREATED).body(dislike);
    }

    // Update given dislike.

    @PutMapping("/dislikes/{id}")
    public ResponseEntity<ArticleDislike> updateArticleLike(@PathVariable Long id,
            @RequestBody ArticleDislike updatedArticleDislike) {
        ArticleDislike fetchedArticleDislike = articleDislikeRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
        fetchedArticleDislike.setDislikeBy(updatedArticleDislike.getDislikeBy());

        articleDislikeRepository.save(fetchedArticleDislike);
        return ResponseEntity.ok(fetchedArticleDislike);
    }

    // Delete given dislike

    @DeleteMapping("/dislikes/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteArticleLike(@PathVariable Long id) {
        ArticleDislike articleDislike = articleDislikeRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
        articleDislikeRepository.delete(articleDislike);
    }

}
