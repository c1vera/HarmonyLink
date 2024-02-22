package com.dongyang.HarmonyLink.MVC.Repository;

import com.dongyang.HarmonyLink.MVC.domain.Article.Entity.ArticleEntity;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostRepositoryCustomImpl implements PostRepositoryCustom {
    private final EntityManager em;


    @Override
    public List<ArticleEntity> findByInputMbti(String mbti) {

        StringBuilder inputMbti = new StringBuilder();

        // X가 설정되어있는 경우
        for(int i = 0; i < mbti.length(); i++) {
            if(!mbti.contains("X")) break; // 현재 mbti에 "X" 없으면 정지.

            if(mbti.charAt(i) == 'X') inputMbti.append("_");
            else inputMbti.append(mbti.charAt(i));

        }

        return em.createQuery("select m from ArticleEntity m where m.type like :expression", ArticleEntity.class) // 어떤 클래스로 제네릭 사용하는지 명시하기.
                .setParameter("expression", inputMbti.toString())
                .getResultList();
    }
}
