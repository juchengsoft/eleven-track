package com.eleven.track.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eleven.track.dto.PointStatQuery;
import com.eleven.track.entity.GotPoint;
import com.eleven.track.entity.GotPointDaily;
import com.eleven.track.mapper.GotPointDailyMapper;
import com.eleven.track.mapper.GotPointMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PointDailyStatService extends ServiceImpl<GotPointDailyMapper, GotPointDaily> {

    private final GotPointDailyMapper gotPointDailyMapper;
    private final GotPointMapper gotPointMapper;

    @Transactional(rollbackFor = Exception.class)
    public void initTodayPointDaily() {
        LocalDate today = LocalDate.now();

        List<GotPoint> pointList = gotPointMapper.selectList(
                new LambdaQueryWrapper<GotPoint>()
                        .eq(GotPoint::getDeleted, 0)
        );
        if (pointList.isEmpty()) {
            return;
        }

        List<GotPointDaily> dailyList = pointList.stream().map(point -> {
            GotPointDaily daily = new GotPointDaily();
            daily.setPointId(point.getId());
            daily.setPointName(point.getPointName());
            daily.setArea(point.getArea());
            daily.setLongitude(point.getLongitude());
            daily.setLatitude(point.getLatitude());
            daily.setAddress(point.getAddress());
            daily.setPointStatus(point.getStatus());

            daily.setStatDate(today);
            daily.setCheckCount(0);
            daily.setLastCheckTime(null);
            return daily;
        }).collect(Collectors.toList());

        batchUpsert(dailyList);
    }

    private void batchUpsert(List<GotPointDaily> dailyList) {
        LocalDate statDate = dailyList.get(0).getStatDate();
        gotPointDailyMapper.delete(
                new LambdaQueryWrapper<GotPointDaily>()
                        .eq(GotPointDaily::getStatDate, statDate)
        );
        for (GotPointDaily daily : dailyList) {
            gotPointDailyMapper.insert(daily);
        }
    }

    public IPage<GotPointDaily> pageList(PointStatQuery query) {
        LambdaQueryWrapper<GotPointDaily> wrapper = buildQueryWrapper(query);
        Page<GotPointDaily> page = new Page<>(query.getCurrent(), query.getSize());
        IPage<GotPointDaily> pageResult = baseMapper.selectPage(page, wrapper);
        pageResult.getRecords().forEach(GotPointDaily::calcCheckLevel);
        return pageResult;
    }

    public GotPointDaily getDetail(Long id) {
        return getById(id);
    }

    public List<GotPointDaily> getExportData(PointStatQuery query) {
        LambdaQueryWrapper<GotPointDaily> wrapper = buildQueryWrapper(query);
        List<GotPointDaily> list = baseMapper.selectList(wrapper);
        list.forEach(GotPointDaily::calcCheckLevel);
        return list;
    }

    private LambdaQueryWrapper<GotPointDaily> buildQueryWrapper(PointStatQuery query) {
        LambdaQueryWrapper<GotPointDaily> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(query.getPointId() != null, GotPointDaily::getPointId, query.getPointId())
                .ge(query.getStartDate() != null, GotPointDaily::getStatDate, query.getStartDate())
                .le(query.getEndDate() != null, GotPointDaily::getStatDate, query.getEndDate());
        if (query.getCheckLevel() != null) {
            switch (query.getCheckLevel()) {
                case "zero":
                    wrapper.apply("check_count = 0");
                    break;
                case "low":
                    wrapper.apply("check_count BETWEEN 1 AND 2");
                    break;
                case "normal":
                    wrapper.apply("check_count BETWEEN 3 AND 5");
                    break;
                case "high":
                    wrapper.apply("check_count > 5");
                    break;
                default:
                    break;
            }
        }
        wrapper.orderByDesc(GotPointDaily::getStatDate);
        return wrapper;
    }

    public void updateDailyAfterCheck(Long pointId) {
        LocalDate today = LocalDate.now();
        LambdaUpdateWrapper<GotPointDaily> updateWrapper = Wrappers.lambdaUpdate();
        updateWrapper.eq(GotPointDaily::getPointId, pointId)
                .eq(GotPointDaily::getStatDate, today)
                .setSql("check_count = check_count + 1")
                .set(GotPointDaily::getLastCheckTime, LocalDateTime.now());
        baseMapper.update(null, updateWrapper);
    }
}