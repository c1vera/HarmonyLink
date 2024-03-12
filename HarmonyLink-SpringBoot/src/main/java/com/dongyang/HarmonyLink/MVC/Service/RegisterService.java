package com.dongyang.HarmonyLink.MVC.Service;

import com.dongyang.HarmonyLink.MVC.domain.User.DTO.UserDTO;
import com.dongyang.HarmonyLink.MVC.domain.User.Entity.UserEntity;
import com.dongyang.HarmonyLink.MVC.Repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@Slf4j
public class RegisterService {

    private UserRepository userRepository;

    public RegisterService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    /** 회원가입한 회원의 정보 불러오기 */
    public UserDTO getInfo(String id) {
        UserEntity entity = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "존재하지 않는 회원입니다."));

        return UserDTO.toDTO(entity);
    }

    /** 회원가입 수행 */
    @Transactional
    public UserDTO register(UserDTO dto) {

        // 해당 사용자가 이미 등록된 경우
        if(userRepository.checkAlreadyRegister(dto.getId(), dto.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"회원가입이 이미 완료된 회원입니다."); // 등록을 수행하지 않음
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
    
    /** 회원정보 수정 */
    @Transactional
    public UserDTO patchUser(UserDTO dto) {
        if(userRepository.findByUserKey(dto.getUserKey()).isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "존재하지 않는 회원입니다.");

        UserEntity result = userRepository.save(UserEntity.toEntity(dto));

        return UserDTO.toDTO(result);
    }

    /** 회원 삭제/탈퇴 */
    @Transactional
    public void deleteUser(UserDTO dto) {
        if(userRepository.findByUserKey(dto.getUserKey()).isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "존재하지 않는 회원입니다.");

        userRepository.delete(UserEntity.toEntity(dto));
    }
}
