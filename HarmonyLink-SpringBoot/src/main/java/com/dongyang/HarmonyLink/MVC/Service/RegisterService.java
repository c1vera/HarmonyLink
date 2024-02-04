package com.dongyang.HarmonyLink.MVC.Service;

import com.dongyang.HarmonyLink.MVC.domain.DTO.UserDTO;
import com.dongyang.HarmonyLink.MVC.domain.Entity.UserEntity;
import com.dongyang.HarmonyLink.MVC.Repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class RegisterService {

    private UserRepository userRepository;

    public RegisterService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDTO getInfo(String id) {
        // 추후 Optional 부분 공부 후에 orElse 대신, Optional<> Wrapper Class로 수행하기
        UserEntity entity = userRepository.findById(id).orElse(null);

        return UserDTO.toDTO(entity);
    }

    @Transactional
    public UserDTO register(UserDTO dto) {
        // DTO to Entity + save
        UserEntity result = userRepository.save(UserEntity.toEntity(dto));

        return UserDTO.toDTO(result);

        /*
        예외 발생 관련은 토의 후에 진행.
        registerRepository.findById(dto.getId());

        registerRepository.findByNickname(dto.getNickname());
        */
    }

//    public UserDTO patch(UserDTO dto) {
//        UserEntity target = registerRepository.findById(dto.getId())
//                .orElseThrow(() -> new IllegalArgumentException("회원 정보 수정 실패. 존재하지 않는 사용자입니다."));
//
//        // dto 매핑 관련 영상 시청 후 수행하기.
//        // DTO, Entity에 mapper method 작성하는 건 올바르지 못함.
//
//
//        return UserDTO.toDTO();
//
//    }
}
