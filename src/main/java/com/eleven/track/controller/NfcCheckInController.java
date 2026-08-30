package com.eleven.track.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.eleven.track.dto.NfcCheckInDTO;
import com.eleven.track.service.NfcCheckInService;
import com.eleven.track.vo.NfcCheckInVO;
import com.eleven.track.vo.ResultVo;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/nfc")
public class NfcCheckInController {

    @Resource
    private NfcCheckInService nfcCheckInService;

    @PostMapping("/checkIn")
    public ResultVo<NfcCheckInVO> checkIn(@Valid @RequestBody NfcCheckInDTO dto) {
        Long userId = StpUtil.getLoginIdAsLong();
        NfcCheckInVO vo = nfcCheckInService.checkIn(dto, userId);
        return ResultVo.success(vo);
    }
}
