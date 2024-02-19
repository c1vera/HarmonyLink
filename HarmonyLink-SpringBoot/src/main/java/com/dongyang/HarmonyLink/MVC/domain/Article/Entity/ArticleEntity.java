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

    public static ArticleEntity toEntity(ArticlePostDTO dto, UserEntity userEntity) {
        return new ArticleEntity(
                dto.getPost_key(),
                dto.getTitle(),
                dto.getType(),
                dto.getContent(),
                dto.getMusic_key(),
                dto.getThumbsUp(),
                null,
                null,
                userEntity);
    }
}
