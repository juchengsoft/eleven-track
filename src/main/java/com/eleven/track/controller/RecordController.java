package com.eleven.track.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.eleven.track.dto.RecordQueryDTO;
import com.eleven.track.entity.GotRecord;
import com.eleven.track.service.PointService;
import com.eleven.track.service.RecordService;
import com.eleven.track.service.UserService;
import com.eleven.track.utils.ExcelUtils;
import com.eleven.track.vo.ResultVo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpHeaders;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/record")
@RequiredArgsConstructor
public class RecordController {

    private final UserService userService;
    private final RecordService recordService;
    private final PointService pointService;

    @SaCheckLogin
    @PostMapping("/check")
    public ResultVo<?> checkPoint(@RequestBody GotRecord record) {
        Long loginUid = StpUtil.getLoginIdAsLong();
        boolean owner = pointService.checkPointOwner(record.getPointId(), loginUid);
        if (!owner) {
            return ResultVo.fail("无权打卡该点位");
        }
        record.setUserId(loginUid);
        record.setCheckTime(LocalDateTime.now());
        recordService.save(record);
        return ResultVo.success("打卡成功");
    }

    @SaCheckLogin
    @GetMapping("/list")
    public ResultVo<?> getRecordList(RecordQueryDTO dto) {
        IPage<GotRecord> page = recordService.pageList(dto);
        return ResultVo.success(page);
    }

    @SaCheckLogin
    @GetMapping("/detail/{id}")
    public ResultVo<GotRecord> getRecordDetail(@PathVariable Long id) {
        GotRecord detail = recordService.getDetail(id);
        return ResultVo.success(detail);
    }

    @SaCheckLogin
    @GetMapping("/export")
    public ResponseEntity<byte[]> exportRecord(RecordQueryDTO dto) {
        List<GotRecord> exportData = recordService.getExportData(dto);
        byte[] excelBytes = ExcelUtils.exportRecord(exportData);

        HttpHeaders headers = new HttpHeaders();
        String fileName = "打卡记录.xlsx";
        String encodedFileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8);
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename*=utf-8''" + encodedFileName);
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return ResponseEntity.ok().headers(headers).body(excelBytes);
    }

    @SaCheckLogin
    @GetMapping("/point")
    public ResultVo<?> getPointSelect() {
        return ResultVo.success(pointService.getPointSelect());
    }

    @SaCheckLogin
    @GetMapping("/user")
    public ResultVo<?> getUserSelect() {
        return ResultVo.success(userService.getUserSelect());
    }
}