package com.eleven.track.controller;

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
    public ResultVo<?> addPoint(@Valid @RequestBody PointSaveDTO dto) {
        pointService.savePoint(dto);
        return ResultVo.success();
    }

    @PostMapping("/update")
    public ResultVo<?> updatePoint(@Valid @RequestBody PointSaveDTO dto) {
        pointService.updatePoint(dto);
        return ResultVo.success();
    }

    @DeleteMapping("/delete/{id}")
    public ResultVo<?> delete(@PathVariable Long id){
        pointService.removeById(id);
        return ResultVo.success();
    }

    @PostMapping("/changeStatus")
    public ResultVo<?> changePointStatus(@Valid @RequestBody PointStatusDTO dto) {
        pointService.changeStatus(dto);
        return ResultVo.success();
    }

}
