package com.dongyang.HarmonyLink.MVC.Repository;

import com.dongyang.HarmonyLink.MVC.domain.Article.Entity.ArticleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

@Repository
public interface PostRepository extends JpaRepository<ArticleEntity, Long>, PostRepositoryCustom {
}
