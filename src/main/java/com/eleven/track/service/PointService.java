package com.eleven.track.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eleven.track.constant.RoleConstant;
import com.eleven.track.dto.PointQueryDTO;
import com.eleven.track.dto.PointSaveDTO;
import com.eleven.track.dto.PointStatusDTO;
import com.eleven.track.entity.GotPoint;
import com.eleven.track.entity.GotUser;
import com.eleven.track.mapper.GotPointMapper;
import com.eleven.track.vo.SelectVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PointService extends ServiceImpl<GotPointMapper, GotPoint> {

    private final UserService userService;

    public IPage<GotPoint> pageList(PointQueryDTO dto) {
        Page<GotPoint> page = new Page<>(dto.getPageNum(), dto.getPageSize());
        LambdaQueryWrapper<GotPoint> wrapper = new LambdaQueryWrapper<>();

        if (StringUtils.hasText(dto.getPointName())) {
            wrapper.like(GotPoint::getPointName, dto.getPointName());
        }
        if (StringUtils.hasText(dto.getArea())) {
            wrapper.like(GotPoint::getArea, dto.getArea());
        }
        if (dto.getStatus() != null) {
            wrapper.eq(GotPoint::getStatus, dto.getStatus());
        }
        wrapper.orderByAsc(GotPoint::getSort).orderByDesc(GotPoint::getCreateTime);
        return baseMapper.selectPage(page, wrapper);
    }

    public void savePoint(PointSaveDTO dto) {
        GotPoint entity = new GotPoint();
        entity.setPointId(dto.getPointId());
        entity.setPointName(dto.getPointName());
        entity.setArea(dto.getArea());
        entity.setLongitude(dto.getLongitude());
        entity.setLatitude(dto.getLatitude());
        entity.setAddress(dto.getAddress());
        entity.setNfcLink(dto.getNfcLink());
        entity.setDepId(dto.getDepId());
        entity.setSort(dto.getSort() == null ? 0 : dto.getSort());
        entity.setRemark(dto.getRemark());
        entity.setStatus(1);
        baseMapper.insert(entity);
    }

    public void updatePoint(PointSaveDTO dto) {
        GotPoint entity = baseMapper.selectById(dto.getId());
        if (entity == null) {
            throw new RuntimeException("点位不存在");
        }
        entity.setPointName(dto.getPointName());
        entity.setArea(dto.getArea());
        entity.setLongitude(dto.getLongitude());
        entity.setLatitude(dto.getLatitude());
        entity.setAddress(dto.getAddress());
        entity.setNfcLink(dto.getNfcLink());
        entity.setDepId(dto.getDepId());
        entity.setSort(dto.getSort());
        entity.setRemark(dto.getRemark());
        baseMapper.updateById(entity);
    }

    public void removePoint(Long id) {
        GotPoint point = baseMapper.selectById(id);
        if (point == null) {
            throw new RuntimeException("点位不存在");
        }
        baseMapper.deleteById(id);
    }

    public void changeStatus(PointStatusDTO dto) {
        GotPoint point = baseMapper.selectById(dto.getId());
        if (point == null) {
            throw new RuntimeException("点位不存在");
        }
        point.setStatus(dto.getStatus());
        baseMapper.updateById(point);
    }

    public List<SelectVO> getPointSelect() {
        List<GotPoint> list = baseMapper.selectList(null);
        return list.stream()
                .map(item -> new SelectVO(item.getId(), item.getPointName()))
                .collect(Collectors.toList());
    }
}