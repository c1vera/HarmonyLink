package com.dongyang.HarmonyLink.MVC.Repository;

import com.dongyang.HarmonyLink.MVC.domain.Article.Entity.ArticleEntity;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom {
    private final EntityManager em;

    @Override
    public List<ArticleEntity> findByUserMbti(String[] mbtiArr) {

        StringBuilder userMbti = new StringBuilder();

        // X가 설정되어있는 경우
        for(int i = 0; i < mbtiArr.length; i++) {
            if(mbtiArr[i].equals("X")) mbtiArr[i] = "_";
            userMbti.append(mbtiArr[i]);
        }

        String expression = "%" + userMbti + "%";

        return em.createQuery("select m from ArticleEntity m where m.type like :expression", ArticleEntity.class) // 어떤 클래스로 제네릭 사용하는지 명시하기.
                .setParameter("expression", expression)
                .getResultList();
    }
}
