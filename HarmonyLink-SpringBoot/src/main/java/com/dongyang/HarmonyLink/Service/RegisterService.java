package com.dongyang.HarmonyLink.Service;

import com.dongyang.HarmonyLink.DTO.UserDTO;
import com.dongyang.HarmonyLink.Entity.UserEntity;
import com.dongyang.HarmonyLink.Repository.RegisterRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class RegisterService {

    private RegisterRepository registerRepository;

    public RegisterService(RegisterRepository registerRepository) {
        this.registerRepository = registerRepository;
    }

    @Transactional
    public UserDTO register(UserDTO dto) {

//        log.info(dto.toString());
        // DTO to Entity + save
        UserEntity result = registerRepository.save(UserEntity.toEntity(dto));

        return UserDTO.toDTO(result);

        /*
        예외 발생 관련은 토의 후에 진행.
        registerRepository.findById(dto.getId());

        registerRepository.findByNickname(dto.getNickname());
        */



    }
}
