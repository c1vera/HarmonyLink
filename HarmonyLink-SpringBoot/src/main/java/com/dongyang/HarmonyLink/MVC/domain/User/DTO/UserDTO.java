package com.dongyang.HarmonyLink.MVC.domain.User.DTO;

import com.dongyang.HarmonyLink.MVC.domain.User.Entity.UserEntity;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
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
