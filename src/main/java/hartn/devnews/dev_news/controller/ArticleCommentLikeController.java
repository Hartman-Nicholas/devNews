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
import hartn.devnews.dev_news.classes.ArticleCommentLike;
import hartn.devnews.dev_news.exception.ResourceNotFoundException;
import hartn.devnews.dev_news.repository.ArticleCommentLikeRepository;
import hartn.devnews.dev_news.repository.ArticleCommentRepository;

@RequestMapping("/articles/comments")
@Controller
public class ArticleCommentLikeController {

    ArticleCommentRepository articleCommentRepository;
    ArticleCommentLikeRepository articleCommentLikeRepository;

    public ArticleCommentLikeController(ArticleCommentRepository articleCommentRepository,
            ArticleCommentLikeRepository articleCommentLikeRepository) {
        this.articleCommentRepository = articleCommentRepository;
        this.articleCommentLikeRepository = articleCommentLikeRepository;
    }

    @GetMapping("/{commentId}/likes")
    public ResponseEntity<Set<ArticleCommentLike>> getArticleCommentLikes(@PathVariable Long commentId) {
        ArticleComment articleComment = articleCommentRepository.findById(commentId)
                .orElseThrow(ResourceNotFoundException::new);
        Set<ArticleCommentLike> articleCommentLikes = articleComment.getArticleCommentLikes();
        return ResponseEntity.ok(articleCommentLikes);
    }

    @PostMapping("/{commentId}/likes")
    public ResponseEntity<ArticleCommentLike> createLikeComment(@PathVariable Long commentId,
            @RequestBody ArticleCommentLike like) {
        ArticleComment articleComment = articleCommentRepository.findById(commentId)
                .orElseThrow(ResourceNotFoundException::new);
        like.setArticleCommentLike(articleComment);
        articleCommentLikeRepository.save(like);
        return ResponseEntity.status(HttpStatus.CREATED).body(like);
    }

    @PutMapping("/likes/{id}")
    public ResponseEntity<ArticleCommentLike> updateCommentLike(@PathVariable Long id,
            @RequestBody ArticleCommentLike updatedCommentLike) {
        ArticleCommentLike fetchedCommentLike = articleCommentLikeRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
        fetchedCommentLike.setLikeBy(updatedCommentLike.getLikeBy());

        articleCommentLikeRepository.save(fetchedCommentLike);
        return ResponseEntity.ok(fetchedCommentLike);
    }

    @DeleteMapping("/likes/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteArticleLike(@PathVariable Long id) {
        ArticleCommentLike articleCommentLike = articleCommentLikeRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
        articleCommentLikeRepository.delete(articleCommentLike);
    }

}
