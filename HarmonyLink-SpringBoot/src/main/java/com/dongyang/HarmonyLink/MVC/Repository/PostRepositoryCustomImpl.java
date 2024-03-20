package com.dongyang.HarmonyLink.MVC.Repository;

import com.dongyang.HarmonyLink.MVC.domain.Article.Entity.ArticleEntity;
import com.dongyang.HarmonyLink.MVC.domain.Article.Entity.TrackEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class PostRepositoryCustomImpl implements PostRepositoryCustom {
    @PersistenceContext
    private final EntityManager em;


    @Override
    public List<ArticleEntity> findByInputMbti(String mbti, Pageable pageable) {
        String query = "select m from ArticleEntity m where m.type like :mbti order by createdDate DESC";
        String countQuery = "select count(a) from ArticleEntity a where a.type like :mbti";


        if(mbti.contains("X")) mbti = mbti.replace('X', '_');

        List<ArticleEntity> mbtiArticleList = em.createQuery(query, ArticleEntity.class)
                .setParameter("mbti", mbti)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        if(mbtiArticleList.size() == 0) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "불러올 게시글이 없습니다.");

        long total = em.createQuery(countQuery, Long.class)
                .setParameter("mbti", mbti)
                .getSingleResult();
        log.info("total 개수 : " + total);




        return mbtiArticleList;
    }
}
