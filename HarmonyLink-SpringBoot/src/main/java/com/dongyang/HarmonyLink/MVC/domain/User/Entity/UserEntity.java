package com.dongyang.HarmonyLink.MVC.domain.User.Entity;

import com.dongyang.HarmonyLink.MVC.domain.User.DTO.UserDTO;
import com.dongyang.HarmonyLink.MVC.domain._Super.Entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class UserEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userKey;

    @Column
    private String id;

    @Column
    private String pw;

    @Column
    private String email;

    @Column
    private String name;

    @Column
    private String nickname;

    @Column
    private String mbti;

//    @Column
//    private LocalDateTime date;
    
    public static UserEntity toEntity(UserDTO dto) {
        return new UserEntity(
                dto.getUserKey(), // == create시는 null, update시에는 해당 사용자 키
                dto.getId(),
                dto.getPw(),
                dto.getEmail(),
                dto.getName(),
                dto.getNickname(),
                dto.getMbti()
        );

    }

    // Spring Security - BCryptEncoder를 통해 입력되어온 비밀번호를 암호화하여 저장하는 함수.
    public void encodePw(String bCryptedPw) {
        this.pw = bCryptedPw;
    }
}
