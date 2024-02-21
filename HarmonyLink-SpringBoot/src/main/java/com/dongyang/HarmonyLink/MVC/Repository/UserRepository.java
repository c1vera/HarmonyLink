package com.dongyang.HarmonyLink.MVC.Repository;

import com.dongyang.HarmonyLink.MVC.domain.User.Entity.UserEntity;
import jakarta.persistence.EntityManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


/** 사용자와 관련된 모든 DB 관련 기능 수행. (회원가입, 로그인 등) */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    /** '모든' 회원 정보 select */
    @Override
    List<UserEntity> findAll();

    /** DB 기준의 id(primary key)로 select*/
    Optional<UserEntity> findByUserKey(Long userKey);

    /** 회원을 '아이디'로 찾기. Long이 아닌 String을 사용한 것이므로,  */
    Optional<UserEntity> findById(String id);

    /** Id, email 동일한 경우 있는지 확인 */
    @Query(value =
            "select * " +
                    "from User_Entity " +
                    "where id = :id or email = :email",
            nativeQuery = true)
    Optional<UserEntity> checkAlreadyRegister(@Param("id") String id, @Param("email") String email);


    /** id, pw로 로그인 시도 */
    @Query(value =
            "select * " +
            "from User_Entity " +
            "where id = :id and pw = :pw",
            nativeQuery = true)
    Optional<UserEntity> tryLogin(@Param("id") String id, @Param("pw") String pw);

    UserEntity findByNickname(String nickname);


}
