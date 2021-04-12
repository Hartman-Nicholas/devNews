package hartn.devnews.dev_news.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import hartn.devnews.dev_news.classes.Article;
import hartn.devnews.dev_news.exception.ResourceNotFoundException;
import hartn.devnews.dev_news.repository.ArticleRepository;

@RequestMapping("/articles")
@RestController
public class ArticleController {

    ArticleRepository articleRepository;

    public ArticleController(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    // Return All Articles

    @GetMapping
    public List<Article> listAllArticles() {
        List<Article> article = articleRepository.findAll();
        return article;
    }

    // Return a specific article based on the provided Id

    @GetMapping("/{id}")
    public ResponseEntity<Article> findOne(@PathVariable Long id) {
        Article article = articleRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        return ResponseEntity.ok(article);
    }

    // Create a new Article

    @PostMapping
    public ResponseEntity<Article> createArticle(@RequestBody Article article) {
        articleRepository.save(article);
        return ResponseEntity.status(HttpStatus.CREATED).body(article);
    }

    // Update the given article

    @PutMapping("/{id}")
    public ResponseEntity<Article> updateArticle(@PathVariable Long id, @RequestBody Article updatedArticle) {
        Article fetchedArticle = articleRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        updatedArticle = fetchedArticle.setUpdateArticleValues(updatedArticle);
        updatedArticle.setId(id);
        articleRepository.save(updatedArticle);
        return ResponseEntity.ok(updatedArticle);
    }

    // Delete the given article

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteArticle(@PathVariable Long id) {
        Article article = articleRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        articleRepository.delete(article);
    }
}
