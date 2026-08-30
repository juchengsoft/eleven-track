package com.eleven.track.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import com.eleven.track.service.WorkbenchService;
import com.eleven.track.vo.ResultVo;
import com.eleven.track.vo.WorkbenchVO;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/workbench")
public class WorkbenchController {

    @Resource
    private WorkbenchService workbenchService;

    @SaCheckLogin
    @GetMapping("/getSummary")
    public ResultVo<?> getSummary() {
        Long userId = StpUtil.getLoginIdAsLong();
        WorkbenchVO vo = workbenchService.getWorkbenchSummary(userId);
        return ResultVo.success(vo);
    }
}
