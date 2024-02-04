package com.dongyang.HarmonyLink.MVC.domain.DTO;

import com.dongyang.HarmonyLink.MVC.domain.Entity.UserEntity;
import lombok.*;

@AllArgsConstructor
@Getter
@ToString
public class UserDTO {

    private Long userKey;

    private String id;

    private String pw;

    private String email;

    private String name;

    private String nickname;

    private String mbti;

//    @DateTimeFormat(pattern = "yyyy-MM-dd")
//    private LocalDateTime date; // BaseTimeEntity 추가에 따른 변경방안 생각해보기.

    public static UserDTO toDTO(UserEntity entity) {
        return new UserDTO(
                entity.getUserKey(),
                entity.getId(),
                entity.getPw(),
                entity.getEmail(),
                entity.getName(),
                entity.getNickname(),
                entity.getMbti()
        );
    }

}
