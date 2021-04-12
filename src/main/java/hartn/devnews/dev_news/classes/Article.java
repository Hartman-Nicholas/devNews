package hartn.devnews.dev_news.classes;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String body;
    private String authorName;

    @OneToMany(mappedBy = "articleOwner")
    private List<ArticleComment> articleComments;

    @OneToMany(mappedBy = "articleLike")
    private List<ArticleLike> articleLikes;

    @OneToMany(mappedBy = "articleDislike")
    private List<ArticleDislike> articleDislikes;

    @ManyToMany
    private List<ArticleTopic> articleTopics;

    public Article() {

    }

    public Article(String title, String body, String authorName) {
        this.title = title;
        this.body = body;
        this.authorName = authorName;

    }

    public Article setUpdateArticleValues(Article articleUpdate) {
        if (articleUpdate.getBody() == null) {
            articleUpdate.setBody(this.getBody());
        }
        if (articleUpdate.getTitle() == null) {
            articleUpdate.setTitle(this.getTitle());
        }
        if (articleUpdate.getAuthorName() == null) {
            articleUpdate.setAuthorName(this.getAuthorName());
        }

        return articleUpdate;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public List<ArticleComment> getArticleComments() {
        return this.articleComments;
    }

    public void setArticleComments(List<ArticleComment> articleComments) {
        this.articleComments = articleComments;
    }

    public List<ArticleLike> getArticleLikes() {
        return this.articleLikes;
    }

    public void setArticleLikes(List<ArticleLike> articleLikes) {
        this.articleLikes = articleLikes;
    }

    public List<ArticleTopic> getArticleTopics() {
        return this.articleTopics;
    }

    public void setArticleTopics(List<ArticleTopic> articleTopics) {
        this.articleTopics = articleTopics;
    }

    public List<ArticleDislike> getArticleDislikes() {
        return this.articleDislikes;
    }

    public void setArticleDislikes(List<ArticleDislike> articleDislikes) {
        this.articleDislikes = articleDislikes;
    }

}
