package com.eleven.track.controller;

import com.eleven.track.service.DashboardService;
import com.eleven.track.vo.ResultVo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {
    private final DashboardService dashboardService;

    @GetMapping("/stat")
    public ResultVo<?> getStat() {
        return ResultVo.success(dashboardService.getDashboardStat());
    }
}
