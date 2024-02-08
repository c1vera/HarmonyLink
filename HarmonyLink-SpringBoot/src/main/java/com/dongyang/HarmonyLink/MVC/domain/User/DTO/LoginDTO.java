package com.dongyang.HarmonyLink.MVC.domain.User.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 용도 : 사용자의 '로그인' 시 사용.
 * */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class LoginDTO {
    private String id;

    private String pw;

}
