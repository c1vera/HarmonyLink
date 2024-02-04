package com.dongyang.HarmonyLink.MVC.domain.Entity;

import com.dongyang.HarmonyLink.MVC.domain.DTO.UserDTO;
import com.dongyang.HarmonyLink.MVC.domain.Entity.superEntitys.BaseTimeEntity;
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
}
