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
public class ArticleDislike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(nullable = false)
    private String dislikeBy;

    @ManyToOne
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @JoinColumn(nullable = false)
    @NotNull
    private Article articleDislike;

    public ArticleDislike() {
    }

    public ArticleDislike(String dislikeBy, Article articleDislike) {

        this.dislikeBy = dislikeBy;
        this.articleDislike = articleDislike;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDislikeBy() {
        return this.dislikeBy;
    }

    public void setDislikeBy(String dislikeBy) {
        this.dislikeBy = dislikeBy;
    }

    public Article getArticleDislike() {
        return this.articleDislike;
    }

    public void setArticleDislike(Article articleDislike) {
        this.articleDislike = articleDislike;
    }

}
