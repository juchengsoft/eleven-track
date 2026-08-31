package com.eleven.track.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.eleven.track.dto.ParkingApplyDTO;
import com.eleven.track.dto.ParkingApplyQueryDTO;
import com.eleven.track.entity.GotParkingApply;
import com.eleven.track.service.ParkingApplyService;
import com.eleven.track.vo.ResultVo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/parkingApply")
@RequiredArgsConstructor
public class ParkingApplyController {

    private final ParkingApplyService parkingApplyService;

    @SaCheckLogin
    @PostMapping("/list")
    public ResultVo<?> getParkingApplyList(@RequestBody ParkingApplyQueryDTO dto) {
        IPage<GotParkingApply> page = parkingApplyService.pageList(dto, dto.getPageNum(), dto.getPageSize());
        return ResultVo.success(page);
    }

    @SaCheckLogin
    @DeleteMapping("/delete/{id}")
    public ResultVo<?> delete(@PathVariable Long id){
        parkingApplyService.removeApply(id);
        return ResultVo.success();
    }

    @PostMapping("/submit")
    public ResultVo<?> submitParkingApply(@Valid @RequestBody ParkingApplyDTO dto) {
        return ResultVo.success(parkingApplyService.submit(dto));
    }
}