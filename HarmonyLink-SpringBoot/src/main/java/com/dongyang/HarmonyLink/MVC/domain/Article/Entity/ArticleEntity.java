package com.dongyang.HarmonyLink.MVC.domain.Article.Entity;

import com.dongyang.HarmonyLink.MVC.domain.Article.DTO.ArticlePostDTO;
import com.dongyang.HarmonyLink.MVC.domain.User.Entity.UserEntity;
import com.dongyang.HarmonyLink.MVC.domain._Super.Entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.boot.model.naming.Identifier;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class ArticleEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long post_key;

    @Column
    private String title;

    @Column
    private String type;

    @Column(columnDefinition = "integer default 0", nullable = false) // null 불가능, 디폴트값 0
    private int view;

    @Column
    private String content;

    @Column
    private String music_key; // api에서 어떻게 받아오느냐에 따라 자료형 변경 예정.

    @Column
    private int thumbsUp;

    /* (관리자에 의한 삭제) 시 사용될 내역*/
    @Column
    private LocalDateTime deletedDate; // 해당 값 유무로 삭제 여부 판단 수행.

    @Column
    private String whyDeleted;


    /* Foreign Key */
    @ManyToOne
    @JoinColumn(name="user_key")
    private UserEntity user;


    /** 게시글의 조회수 1 증가. */
    public void increaseView() {
        this.view += 1;
    }

    public static ArticleEntity toEntity(ArticlePostDTO dto, UserEntity userEntity) {
        return new ArticleEntity(
                dto.getPost_key(),
                dto.getTitle(),
                dto.getType(),
                dto.getView(),
                dto.getContent(),
                dto.getMusic_key(),
                dto.getThumbsUp(),
                null,
                null,
                userEntity);
    }

    /** 사용자가 게시글을 수정함에 따라 변경되는 값들을 업데이트 */
    public void patchEntity(ArticleEntity newArticle) {
        if(newArticle.title != null) this.title = newArticle.getTitle();
        this.view = newArticle.getView(); // int는 기본값 0이므로 null 없음
        if(newArticle.content != null) this.content = newArticle.getContent();
        if(newArticle.music_key != null) this.music_key = newArticle.getMusic_key();
        this.thumbsUp = newArticle.getThumbsUp(); // int는 기본값 0이므로 null 없음
    }
}
