package com.eleven.track.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.eleven.track.dto.PointQueryDTO;
import com.eleven.track.dto.PointSaveDTO;
import com.eleven.track.dto.PointStatusDTO;
import com.eleven.track.entity.GotPoint;
import com.eleven.track.service.PointService;
import com.eleven.track.vo.ResultVo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/point")
@RequiredArgsConstructor
public class PointController {

    private final PointService pointService;

    @PostMapping("/list")
    public ResultVo<?> getPointList(@RequestBody PointQueryDTO dto) {
        IPage<GotPoint> page = pointService.pageList(dto);
        return ResultVo.success(page);
    }

    @PostMapping("/add")
    public ResultVo<Void> addPoint(@Valid @RequestBody PointSaveDTO dto) {
        pointService.savePoint(dto);
        return ResultVo.success();
    }

    @PostMapping("/update")
    public ResultVo<Void> updatePoint(@Valid @RequestBody PointSaveDTO dto) {
        pointService.updatePoint(dto);
        return ResultVo.success();
    }

    @PostMapping("/delete")
    public ResultVo<Void> deletePoint(@RequestBody Long id) {
        pointService.removePoint(id);
        return ResultVo.success();
    }

    @PostMapping("/changeStatus")
    public ResultVo<Void> changePointStatus(@Valid @RequestBody PointStatusDTO dto) {
        pointService.changeStatus(dto);
        return ResultVo.success();
    }

}
