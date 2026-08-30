package com.eleven.track.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.eleven.track.dto.PointStatQuery;
import com.eleven.track.entity.GotPointDaily;
import com.eleven.track.service.PointDailyStatService;
import com.eleven.track.utils.ExcelUtils;
import com.eleven.track.vo.ResultVo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequestMapping("/api/point-stat")
@RequiredArgsConstructor
public class PointStatController {

    private final PointDailyStatService pointDailyStatService;

    @SaCheckLogin
    @GetMapping("/list")
    public ResultVo<?> getPointStatList(PointStatQuery dto) {
        IPage<GotPointDaily> page = pointDailyStatService.pageList(dto);
        return ResultVo.success(page);
    }

    @SaCheckLogin
    @GetMapping("/detail/{id}")
    public ResultVo<?> getPointStatDetail(@PathVariable Long id) {
        GotPointDaily detail = pointDailyStatService.getDetail(id);
        return ResultVo.success(detail);
    }

    @SaCheckLogin
    @GetMapping("/export")
    public ResponseEntity<byte[]> exportPointStat(PointStatQuery dto) {
        List<GotPointDaily> exportData = pointDailyStatService.getExportData(dto);
        byte[] excelBytes = ExcelUtils.exportPointStat(exportData);

        HttpHeaders headers = new HttpHeaders();
        String fileName = "点位打卡统计.xlsx";
        String encodedFileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8);
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename*=utf-8''" + encodedFileName);
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return ResponseEntity.ok().headers(headers).body(excelBytes);
    }
}