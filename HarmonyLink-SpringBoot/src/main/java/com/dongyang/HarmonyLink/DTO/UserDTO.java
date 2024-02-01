package com.dongyang.HarmonyLink.DTO;

import com.dongyang.HarmonyLink.Entity.UserEntity;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;

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
