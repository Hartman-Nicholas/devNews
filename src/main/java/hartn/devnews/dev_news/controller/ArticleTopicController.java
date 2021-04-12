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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import hartn.devnews.dev_news.classes.Article;
import hartn.devnews.dev_news.classes.ArticleTopic;
import hartn.devnews.dev_news.exception.ResourceNotFoundException;
import hartn.devnews.dev_news.repository.ArticleRepository;
import hartn.devnews.dev_news.repository.ArticleTopicRepository;

@RestController
public class ArticleTopicController {

    ArticleTopicRepository articleTopicRepository;
    ArticleRepository articleRepository;

    public ArticleTopicController(ArticleTopicRepository articleTopicRepository, ArticleRepository articleRepository) {
        this.articleTopicRepository = articleTopicRepository;
        this.articleRepository = articleRepository;
    }

    // Return all topics

    @GetMapping("/topics")
    public ResponseEntity<List<ArticleTopic>> listAllTopics() {
        List<ArticleTopic> articleTopic = articleTopicRepository.findAll();
        return ResponseEntity.ok(articleTopic);
    }

    // Return all topics associated with article given by articleId.

    @GetMapping("/articles/{articleId}/topics")
    public ResponseEntity<List<ArticleTopic>> listAllTopicsUnderArticle(@PathVariable Long articleId) {
        Article article = articleRepository.findById(articleId).orElseThrow(ResourceNotFoundException::new);
        return ResponseEntity.ok(article.getArticleTopics());
    }

    // Return all articles associated with the topic given by topicId.

    @GetMapping("/topics/{topicId}/articles")
    public ResponseEntity<List<Article>> listAllArticlesUnderTopic(@PathVariable Long topicId) {
        ArticleTopic topic = articleTopicRepository.findById(topicId).orElseThrow(ResourceNotFoundException::new);
        return ResponseEntity.ok(topic.getArticle());
    }

    // Create a new topic.

    // Associate the topic with the article given by articleId. If topic does not
    // already exist, it is created.

    @PostMapping("/articles/{articleId}/topics")
    public ResponseEntity<Article> createArticleTopicAssociation(@PathVariable Long articleId,
            @RequestBody ArticleTopic articleTopic) {
        Article article = articleRepository.findById(articleId).orElseThrow(ResourceNotFoundException::new);

        // Checks if Topic already exists on the Article so double topic is // not
        // added.

        List<ArticleTopic> topics = article.getArticleTopics();
        for (ArticleTopic articleTopic2 : topics) {
            if (articleTopic2.getName().equals(articleTopic.getName())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(article);
            }
        }

        // Checks if the article Topic already exists in the
        // articleTopicRepositoty, if it doesnt exist adds it to the
        // Repository.

        ArticleTopic articleTopicSearch = articleTopicRepository.findByName(articleTopic.getName());
        if (articleTopicSearch == null) {
            articleTopicRepository.save(articleTopic);
            article.getArticleTopics().add(articleTopic);
        } else {
            article.getArticleTopics().add(articleTopicSearch);
        }

        articleRepository.save(article);

        return ResponseEntity.status(HttpStatus.CREATED).body(article);
    }

    // Create a new topic.

    @PostMapping("/topics")
    public ResponseEntity<ArticleTopic> createArticleTopic(@RequestBody ArticleTopic articleTopic) {

        ArticleTopic articleTopicSearch = articleTopicRepository.findByName(articleTopic.getName());
        if (articleTopicSearch == null) {
            articleTopicRepository.save(articleTopic);
            return ResponseEntity.status(HttpStatus.CREATED).body(articleTopic);
        } else {
            articleTopicRepository.save(articleTopicSearch);
            return ResponseEntity.status(HttpStatus.CREATED).body(articleTopicSearch);
        }
    }

    // Update the given topic.

    @PutMapping("/topics/{id}")
    public ResponseEntity<ArticleTopic> updateArticleTopic(@PathVariable Long id,
            @RequestBody ArticleTopic updatedArticleTopic) {
        articleTopicRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        updatedArticleTopic.setId(id);
        articleTopicRepository.save(updatedArticleTopic);
        return ResponseEntity.ok(updatedArticleTopic);
    }

    // Delete the given topic.

    @DeleteMapping("/topics/{topicId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteArticleTopic(@PathVariable Long topicId) {
        ArticleTopic articleTopic = articleTopicRepository.findById(topicId)
                .orElseThrow(ResourceNotFoundException::new);
        articleTopicRepository.delete(articleTopic);
    }

    // Delete the association of a topic for the given article. The topic & article
    // themselves remain.

    @DeleteMapping("/articles/{articleId}/topics/{topicId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteArticleTopicAssociation(@PathVariable Long articleId, @PathVariable Long topicId) {

        Article article = articleRepository.findById(articleId).orElseThrow(ResourceNotFoundException::new);
        ArticleTopic articleTopic = articleTopicRepository.findById(topicId)
                .orElseThrow(ResourceNotFoundException::new);

        if (article.getArticleTopics().contains(articleTopic)) {
            article.getArticleTopics().remove(articleTopic);
            articleRepository.save(article);
        } else {
            throw new ResourceNotFoundException();
        }
    }

}
