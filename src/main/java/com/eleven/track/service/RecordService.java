package com.eleven.track.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eleven.track.dto.RecordQueryDTO;
import com.eleven.track.entity.GotRecord;
import com.eleven.track.mapper.GotRecordMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecordService extends ServiceImpl<GotRecordMapper, GotRecord> {

    public IPage<GotRecord> pageList(RecordQueryDTO dto) {
        LambdaQueryWrapper<GotRecord> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(dto.getPointId() != null, GotRecord::getPointId, dto.getPointId())
                .eq(dto.getUserId() != null, GotRecord::getUserId, dto.getUserId())
                .eq(dto.getCheckStatus() != null, GotRecord::getCheckStatus, dto.getCheckStatus())
                .ge(dto.getStartTime() != null, GotRecord::getCheckTime, dto.getStartTime())
                .le(dto.getEndTime() != null, GotRecord::getCheckTime, dto.getEndTime())
                .orderByDesc(GotRecord::getCheckTime);
        Page<GotRecord> page = new Page<>(dto.getCurrent(), dto.getSize());
        return baseMapper.selectPage(page, wrapper);
    }

    public GotRecord getDetail(Long id) {
        return getById(id);
    }

    public List<GotRecord> getExportData(RecordQueryDTO dto) {
        LambdaQueryWrapper<GotRecord> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(dto.getPointId() != null, GotRecord::getPointId, dto.getPointId())
                .eq(dto.getUserId() != null, GotRecord::getUserId, dto.getUserId())
                .eq(dto.getCheckStatus() != null, GotRecord::getCheckStatus, dto.getCheckStatus())
                .ge(dto.getStartTime() != null, GotRecord::getCheckTime, dto.getStartTime())
                .le(dto.getEndTime() != null, GotRecord::getCheckTime, dto.getEndTime())
                .orderByDesc(GotRecord::getCheckTime);
        return baseMapper.selectList(wrapper);
    }
}