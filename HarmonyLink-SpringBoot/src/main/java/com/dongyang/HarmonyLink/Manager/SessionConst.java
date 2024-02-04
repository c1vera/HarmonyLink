package com.dongyang.HarmonyLink.Manager;

import java.util.UUID;

/* 사용자 인증용 쿠키의 이름을 유추할 수 없게 하기 위해, 쿠키 이름도 UUID로 작성.
 * 이를 위해, 상수 인터페이스를 사용하도록 한다.*/
/** 사용자 로그인 시 클라이언트에 전송할 쿠키 이름의 무작위성을 위한 상수 인터페이스 */
public interface SessionConst {

    /** 사용자 로그인 시 클라이언트에 전송할 '쿠키 이름'의 무작위성을 위한 상수 */
    public static final String COOKIE_NAME = UUID.randomUUID().toString();
}
