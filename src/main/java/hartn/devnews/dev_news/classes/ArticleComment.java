package hartn.devnews.dev_news.classes;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
public class ArticleComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(nullable = false)
    private String body;
    @JoinColumn(nullable = false)
    private String authorName;

    @ManyToOne
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @JoinColumn(nullable = false)
    @NotNull
    private Article articleOwner;

    @OneToMany(mappedBy = "articleCommentLike")
    private Set<ArticleCommentLike> articleCommentLikes;

    @OneToMany(mappedBy = "articleCommentDislike")
    private Set<ArticleCommentDislike> articleCommentDislikes;

    public ArticleComment() {
    }

    public ArticleComment(String body, String authorName) {
        this.body = body;
        this.authorName = authorName;
    }

    public ArticleComment setUpdateArticleCommentValues(ArticleComment articleCommentUpdate) {
        if (articleCommentUpdate.getBody() == null) {
            articleCommentUpdate.setBody(this.getBody());
        }
        if (articleCommentUpdate.getAuthorName() == null) {
            articleCommentUpdate.setAuthorName(this.getAuthorName());
        }
        if (articleCommentUpdate.getArticleOwner() == null) {
            articleCommentUpdate.setArticleOwner(this.getArticleOwner());
        }

        return articleCommentUpdate;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBody() {
        return this.body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getAuthorName() {
        return this.authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public Article getArticleOwner() {
        return this.articleOwner;
    }

    public void setArticleOwner(Article articleOwner) {
        this.articleOwner = articleOwner;
    }

    public Set<ArticleCommentLike> getArticleCommentLikes() {
        return this.articleCommentLikes;
    }

    public void setArticleCommentLikes(Set<ArticleCommentLike> articleCommentLikes) {
        this.articleCommentLikes = articleCommentLikes;
    }

    public Set<ArticleCommentDislike> getArticleCommentDislikes() {
        return this.articleCommentDislikes;
    }

    public void setArticleCommentDislikes(Set<ArticleCommentDislike> articleCommentDislikes) {
        this.articleCommentDislikes = articleCommentDislikes;
    }

}
