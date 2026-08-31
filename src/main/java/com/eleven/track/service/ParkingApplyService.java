package com.eleven.track.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eleven.track.dto.ParkingApplyDTO;
import com.eleven.track.dto.ParkingApplyQueryDTO;
import com.eleven.track.entity.GotParkingApply;
import com.eleven.track.mapper.ParkingApplyMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ParkingApplyService extends ServiceImpl<ParkingApplyMapper, GotParkingApply> {

    private final ParkingApplyMapper parkingApplyMapper;

    @Transactional(rollbackFor = Exception.class)
    public GotParkingApply submit(ParkingApplyDTO dto) {
        if (dto.getApplyStart().isAfter(dto.getApplyEnd())) {
            throw new RuntimeException("停车开始时间不能晚于结束时间");
        }
        GotParkingApply entity = new GotParkingApply();
        BeanUtils.copyProperties(dto, entity);
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String shortUuid = UUID.randomUUID().toString().replace("-", "").substring(0, 8).toUpperCase();
        entity.setApplyNo(date + shortUuid);
        save(entity);
        return entity;
    }

    public IPage<GotParkingApply> pageList(ParkingApplyQueryDTO dto, long current, long size) {
        Page<GotParkingApply> page = new Page<>(current, size);
        LambdaQueryWrapper<GotParkingApply> wrapper = new LambdaQueryWrapper<>();

        if (StringUtils.hasText(dto.getKeyword())) {
            wrapper.and(w -> w.like(GotParkingApply::getVisitorName, dto.getKeyword())
                    .or().like(GotParkingApply::getVisitorPhone, dto.getKeyword())
                    .or().like(GotParkingApply::getPlateNumber, dto.getKeyword())
            );
        }
        if (StringUtils.hasText(dto.getHouseNo())) {
            wrapper.like(GotParkingApply::getHouseNo, dto.getHouseNo());
        }
        if (StringUtils.hasText(dto.getBeginTime())) {
            wrapper.ge(GotParkingApply::getCreateTime, dto.getBeginTime());
        }
        if (StringUtils.hasText(dto.getEndTime())) {
            wrapper.le(GotParkingApply::getCreateTime, dto.getEndTime() + " 23:59:59");
        }
        wrapper.orderByDesc(GotParkingApply::getCreateTime);

        return baseMapper.selectPage(page, wrapper);
    }

    public void removeApply(Long id) {
        GotParkingApply dbEntity = getById(id);
        if (dbEntity == null) {
            throw new RuntimeException("申请记录不存在");
        }
        GotParkingApply entity = new GotParkingApply();
        entity.setId(id);
        entity.setDelFlag(1);
        updateById(entity);
    }
}
