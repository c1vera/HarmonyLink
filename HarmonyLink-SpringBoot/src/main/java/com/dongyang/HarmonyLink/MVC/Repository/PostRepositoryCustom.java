package com.dongyang.HarmonyLink.MVC.Repository;


import com.dongyang.HarmonyLink.MVC.domain.Article.Entity.ArticleEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

// 확장을 위한 인터페이스므로, 해당 인터페이스는 Repository 어노테이션 사용 X
public interface PostRepositoryCustom {

    public List<ArticleEntity> findByUserMbti(String[] mbtiArr);
}
