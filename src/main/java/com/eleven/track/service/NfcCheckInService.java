package com.eleven.track.service;

import com.eleven.track.dto.NfcCheckInDTO;
import com.eleven.track.entity.GotPoint;
import com.eleven.track.entity.GotRecord;
import com.eleven.track.mapper.GotPointMapper;
import com.eleven.track.mapper.GotRecordMapper;
import com.eleven.track.vo.NfcCheckInVO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class NfcCheckInService {

    private static final double MAX_ALLOW_DISTANCE = 30.0;

    @Resource
    private GotPointMapper gotPointMapper;
    @Resource
    private GotRecordMapper gotRecordMapper;
    @Resource
    private PointDailyStatService pointDailyStatService;

    public NfcCheckInVO checkIn(NfcCheckInDTO dto, Long userId) {
        NfcCheckInVO vo = new NfcCheckInVO();

        GotPoint point = gotPointMapper.selectByPointId(dto.getPointId());
        if (point == null || point.getDeleted() == 1 || !Integer.valueOf(1).equals(point.getStatus())) {
            vo.setSuccess(false);
            vo.setMsg("点位不存在或已停用，请联系管理员");
            return vo;
        }
        String dbDepIds = point.getDepIds();
        if (!containDep(dbDepIds, dto.getDepId())) {
            vo.setSuccess(false);
            vo.setMsg("该点位不属于当前操作部门，禁止操作");
            return vo;
        }
        vo.setPointName(point.getPointName());
        vo.setArea(point.getArea());
        vo.setAddress(point.getAddress());

        Double distance = (double) 0;
        if (point.getLatitude() != null && point.getLongitude() != null) {
            distance = calcDistance(
                    point.getLatitude().doubleValue(),
                    point.getLongitude().doubleValue(),
                    dto.getLatitude().doubleValue(),
                    dto.getLongitude().doubleValue()
            );
            vo.setDistanceMeter(distance);

            if (distance > MAX_ALLOW_DISTANCE) {
                vo.setSuccess(false);
                vo.setMsg(String.format("距离点位过远，当前距离：%.1f米，允许最大%.0f米", distance, MAX_ALLOW_DISTANCE));
                return vo;
            }
        } else {
            vo.setDistanceMeter(null);
            if (dto.getLatitude() != null && dto.getLongitude() != null
                    && dto.getLatitude().compareTo(BigDecimal.ZERO) != 0
                    && dto.getLongitude().compareTo(BigDecimal.ZERO) != 0) {

                point.setLatitude(dto.getLatitude());
                point.setLongitude(dto.getLongitude());
                gotPointMapper.updateById(point);
            }
        }

        GotRecord record = new GotRecord();
        record.setPointId(point.getId());
        record.setUserId(userId);
        record.setArea(point.getArea());
        record.setCheckTime(LocalDateTime.now());
        record.setLongitude(dto.getLongitude());
        record.setLatitude(dto.getLatitude());
        record.setRemark(dto.getRemark());
        record.setCheckStatus(1);
        record.setDistanceMeter(distance);
        record.setDeviceInfo(dto.getDeviceInfo());
        gotRecordMapper.insert(record);

        pointDailyStatService.updateDailyAfterCheck(point.getId());

        vo.setSuccess(true);
        vo.setMsg("打卡成功");
        vo.setCheckTime(record.getCheckTime());
        return vo;
    }

    private boolean containDep(String depIdsStr, Long depId){
        if(!StringUtils.hasText(depIdsStr) || depId == null){
            return false;
        }
        Set<Long> depIdSet = Arrays.stream(depIdsStr.split(","))
                .map(Long::valueOf)
                .collect(Collectors.toSet());
        return depIdSet.contains(depId);
    }

    private Double calcDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371000;
        double latRad1 = Math.toRadians(lat1);
        double latRad2 = Math.toRadians(lat2);
        double deltaLat = Math.toRadians(lat2 - lat1);
        double deltaLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(deltaLat / 2) * Math.sin(deltaLat / 2)
                + Math.cos(latRad1) * Math.cos(latRad2)
                * Math.sin(deltaLon / 2) * Math.sin(deltaLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }
}