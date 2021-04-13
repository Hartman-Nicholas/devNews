package hartn.devnews.dev_news.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import hartn.devnews.dev_news.model.ArticleTopic;

public interface ArticleTopicRepository extends JpaRepository<ArticleTopic, Long> {

    ArticleTopic findByName(String name);

}
