package hartn.devnews.dev_news.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
public class ArticleCommentDislike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(nullable = false)
    private String DislikeBy;

    @ManyToOne
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @JoinColumn(nullable = false)
    @NotNull
    private ArticleComment articleCommentDislike;

    public ArticleCommentDislike() {
    }

    public ArticleCommentDislike(String DislikeBy) {

        this.DislikeBy = DislikeBy;

    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDislikeBy() {
        return this.DislikeBy;
    }

    public void setDislikeBy(String DislikeBy) {
        this.DislikeBy = DislikeBy;
    }

    public ArticleComment getArticleCommentDislike() {
        return this.articleCommentDislike;
    }

    public void setArticleCommentDislike(ArticleComment articleCommentDislike) {
        this.articleCommentDislike = articleCommentDislike;
    }

}
