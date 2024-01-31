package com.dongyang.HarmonyLink.Repository;

import com.dongyang.HarmonyLink.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RegisterRepository extends JpaRepository<UserEntity, Long> {

    /** '모든' 회원 정보 select */
    List<UserEntity> findAllByUserKey(Long userKey);

    /** 회원을 '아이디'로 찾기 */
    UserEntity findById(String id);

    UserEntity findByNickname(String nickname);
}
