package com.dongyang.HarmonyLink.MVC.Repository;

import com.dongyang.HarmonyLink.MVC.domain.Article.Entity.ArticleEntity;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostRepositoryCustomImpl implements PostRepositoryCustom {
    private final EntityManager em;


    @Override
    public Page<ArticleEntity> findByInputMbti(String mbti, Pageable pageable) {
        String query = "select m from ArticleEntity m where m.type like :expression";
        String countQuery = "select count(a) from ArticleEntity a where a.type = :mbti";

        List<ArticleEntity> mbtiArticleList = null;
        // 제공된 mbti 타입이 이미 완성형인 경우.
        if(!mbti.contains("X")) {
            // "select m from ArticleEntity m where m.type = :mbti"
            mbtiArticleList = em.createQuery("select m from ArticleEntity m where m.type like :mbti", ArticleEntity.class)
                    .setParameter("mbti", mbti)
                    .setFirstResult((int) pageable.getOffset())
                    .setMaxResults(pageable.getPageSize())
                    .getResultList();
        }
        else {
            mbti = mbti.replace('X', '_');
            mbtiArticleList = em.createQuery(query, ArticleEntity.class) // 어떤 클래스로 제네릭 사용하는지 명시하기.
                    .setParameter("expression", mbti)
                    .getResultList();
        }

        if(mbtiArticleList == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "쿼리 요청이 정상동작하지 않음");

        long total = em.createQuery(countQuery, Long.class)
                .setParameter("mbti", mbti)
                .getSingleResult();



        return new PageImpl<>(mbtiArticleList, pageable, total);



    }
}
