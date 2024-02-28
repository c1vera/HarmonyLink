package com.dongyang.HarmonyLink.MVC.Repository;


import com.dongyang.HarmonyLink.MVC.domain.Article.Entity.ArticleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

// 확장을 위한 인터페이스므로, 해당 인터페이스는 Repository 어노테이션 사용 X
public interface PostRepositoryCustom {

    public Page<ArticleEntity> findByInputMbti(String mbti, Pageable pageable);
}
