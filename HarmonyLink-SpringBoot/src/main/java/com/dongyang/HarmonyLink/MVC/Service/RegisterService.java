package com.dongyang.HarmonyLink.MVC.Service;

import com.dongyang.HarmonyLink.MVC.domain.User.DTO.UserDTO;
import com.dongyang.HarmonyLink.MVC.domain.User.Entity.UserEntity;
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
        UserEntity userEntity = UserEntity.toEntity(dto);

        // 해당 사용자가 이미 등록된 경우
        if(userRepository.checkAlreadyRegister(dto.getId(), dto.getEmail()).orElse(null) != null) {
            throw new RuntimeException("회원가입이 이미 완료된 회원입니다."); // 등록을 수행하지 않음
        }

        // DTO to Entity + save
        UserEntity result = userRepository.save(UserEntity.toEntity(dto));

        return UserDTO.toDTO(result);

        /*
        예외 발생 관련은 토의 후에 진행.
        registerRepository.findById(dto.getId());

        registerRepository.findByNickname(dto.getNickname());
        */
    }

    @Transactional
    public UserDTO patchUser(UserDTO dto) {
        UserEntity userEntity = UserEntity.toEntity(dto);

        // 예외처리 부문


        UserEntity result = userRepository.save(UserEntity.toEntity(dto));

        return UserDTO.toDTO(result);
    }

    @Transactional
    public void deleteUser(UserDTO dto) {
        userRepository.delete(UserEntity.toEntity(dto));
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
