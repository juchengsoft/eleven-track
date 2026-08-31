package com.eleven.track.service;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eleven.track.dto.ParkingApplyAuditDTO;
import com.eleven.track.dto.ParkingApplyDTO;
import com.eleven.track.dto.ParkingApplyQueryDTO;
import com.eleven.track.entity.GotParkingApply;
import com.eleven.track.entity.GotUser;
import com.eleven.track.mapper.GotUserMapper;
import com.eleven.track.mapper.ParkingApplyMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ParkingApplyService extends ServiceImpl<ParkingApplyMapper, GotParkingApply> {

    private final ParkingApplyMapper parkingApplyMapper;
    private final GotUserMapper userMapper;

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
                    .or().like(GotParkingApply::getHouseNo, dto.getKeyword())
                    .or().like(GotParkingApply::getPlateNumber, dto.getKeyword())
            );
        }
        if (dto.getApplyStatus() != null) {
            wrapper.like(GotParkingApply::getHouseNo, dto.getApplyStatus());
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

    public IPage<GotParkingApply> auditQueryPage(Page<GotParkingApply> page, ParkingApplyQueryDTO dto) {
        LambdaQueryWrapper<GotParkingApply> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(dto.getKeyword())) {
            wrapper.and(w -> w
                    .like(GotParkingApply::getVisitorName, dto.getKeyword())
                    .or().like(GotParkingApply::getVisitorPhone, dto.getKeyword())
                    .or().like(GotParkingApply::getPlateNumber, dto.getKeyword())
                    .or().like(GotParkingApply::getHouseNo, dto.getKeyword())
            );
        }
        wrapper.eq(dto.getApplyStatus() != null, GotParkingApply::getApplyStatus, dto.getApplyStatus());
        if (StringUtils.hasText(dto.getBeginTime())) {
            wrapper.ge(GotParkingApply::getCreateTime, dto.getBeginTime());
        }
        if (StringUtils.hasText(dto.getEndTime())) {
            wrapper.le(GotParkingApply::getCreateTime, dto.getEndTime() + " 23:59:59");
        }
        wrapper.orderByDesc(GotParkingApply::getCreateTime);
        return baseMapper.selectPage(page, wrapper);
    }


    public void doAudit(ParkingApplyAuditDTO auditDTO) {
        GotParkingApply apply = this.getById(auditDTO.getId());
        if (apply == null) {
            throw new RuntimeException("申请记录不存在");
        }
        if (!Integer.valueOf(0).equals(apply.getApplyStatus())) {
            throw new RuntimeException("该申请不是待审批状态，无法审批");
        }

        GotParkingApply updateEntity = new GotParkingApply();
        updateEntity.setId(auditDTO.getId());
        updateEntity.setApplyStatus(auditDTO.getAuditResult());
        updateEntity.setAuditRemark(auditDTO.getAuditRemark());
        updateEntity.setAuditTime(LocalDateTime.now());
        updateEntity.setAuditUserId(StpUtil.getLoginIdAsLong());
        GotUser user = userMapper.selectById(StpUtil.getLoginIdAsLong());
        updateEntity.setAuditUserName(user.getNickName());

        this.updateById(updateEntity);
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
