package com.dongyang.HarmonyLink.ApiContorller;

import com.dongyang.HarmonyLink.DTO.UserDTO;
import com.dongyang.HarmonyLink.Service.RegisterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1") // controller 또한 매핑 가능. 이를 통해 컨트롤러 별 버전 유지도 가능.
@Slf4j
public class RegisterApiController {
    private RegisterService registerService;

    public RegisterApiController(RegisterService registerService) {
        this.registerService = registerService;
    }

    @PostMapping("/user/requestRegister")
    public void register(@RequestBody UserDTO dto) {
        log.info(dto.toString());

        // 서비스 호출
        UserDTO resultDTO = registerService.register(dto);
    }


}
