package com.dongyang.HarmonyLink.MVC.domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * 용도 : 사용자의 '로그인' 시 사용.
 * */
@AllArgsConstructor
@Getter
@ToString
public class LoginDTO {
    private String id;

    private String pw;
    public LoginDTO() {

    }

}
