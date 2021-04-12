package hartn.devnews.dev_news.controller;

import java.util.Set;

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

import hartn.devnews.dev_news.classes.ArticleComment;
import hartn.devnews.dev_news.classes.ArticleCommentDislike;
import hartn.devnews.dev_news.exception.ResourceNotFoundException;
import hartn.devnews.dev_news.repository.ArticleCommentDislikeRepository;
import hartn.devnews.dev_news.repository.ArticleCommentRepository;

@RequestMapping("/articles/comments")
@Controller
public class ArticleCommentDislikeController {

    ArticleCommentRepository articleCommentRepository;
    ArticleCommentDislikeRepository articleCommentDislikeRepository;

    public ArticleCommentDislikeController(ArticleCommentRepository articleCommentRepository,
            ArticleCommentDislikeRepository articleCommentDislikeRepository) {
        this.articleCommentRepository = articleCommentRepository;
        this.articleCommentDislikeRepository = articleCommentDislikeRepository;
    }

    // Return all dislikes on a given comment

    @GetMapping("/{commentId}/dislikes")
    public ResponseEntity<Set<ArticleCommentDislike>> getArticleCommentDislikes(@PathVariable Long commentId) {
        ArticleComment articleComment = articleCommentRepository.findById(commentId)
                .orElseThrow(ResourceNotFoundException::new);
        Set<ArticleCommentDislike> articleCommentDislikes = articleComment.getArticleCommentDislikes();
        return ResponseEntity.ok(articleCommentDislikes);
    }

    // Create a dislike on a give comment

    @PostMapping("/{commentId}/dislikes")
    public ResponseEntity<ArticleCommentDislike> createDislikeComment(@PathVariable Long commentId,
            @RequestBody ArticleCommentDislike disLike) {
        ArticleComment articleComment = articleCommentRepository.findById(commentId)
                .orElseThrow(ResourceNotFoundException::new);
        disLike.setArticleCommentDislike(articleComment);
        articleCommentDislikeRepository.save(disLike);
        return ResponseEntity.status(HttpStatus.CREATED).body(disLike);
    }

    // Update the given dislike

    @PutMapping("/dislikes/{id}")
    public ResponseEntity<ArticleCommentDislike> updateCommentDislike(@PathVariable Long id,
            @RequestBody ArticleCommentDislike updatedCommentDislike) {
        ArticleCommentDislike fetchedCommentDislike = articleCommentDislikeRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
        fetchedCommentDislike.setDislikeBy(updatedCommentDislike.getDislikeBy());

        articleCommentDislikeRepository.save(fetchedCommentDislike);
        return ResponseEntity.ok(fetchedCommentDislike);
    }

    // Delete the given dislike

    @DeleteMapping("/dislikes/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteArticleLike(@PathVariable Long id) {
        ArticleCommentDislike articleCommentDislike = articleCommentDislikeRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
        articleCommentDislikeRepository.delete(articleCommentDislike);
    }

}
