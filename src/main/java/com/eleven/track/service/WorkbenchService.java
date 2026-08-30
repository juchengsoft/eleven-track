package com.eleven.track.service;

import com.eleven.track.mapper.WorkbenchMapper;
import com.eleven.track.vo.WorkbenchSummaryJoinVO;
import com.eleven.track.vo.WorkbenchVO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WorkbenchService {

    @Resource
    private WorkbenchMapper workbenchMapper;

    public WorkbenchVO getWorkbenchSummary(Long userId) {
        WorkbenchVO vo = new WorkbenchVO();
        LocalDate today = LocalDate.now();

        List<WorkbenchSummaryJoinVO> joinList = workbenchMapper.queryWorkbenchOnce(userId, today);

        if (joinList.isEmpty()) {
            vo.setTodayTotal(0);
            vo.setTodayChecked(0);
            vo.setRecentList(List.of());
        } else {
            WorkbenchSummaryJoinVO first = joinList.get(0);
            vo.setTodayTotal(first.getTodayTotal());
            vo.setTodayChecked(first.getTodayChecked());

            List<WorkbenchVO.WorkbenchRecordItemVO> recentList = joinList.stream()
                    .filter(item -> item.getId() != null)
                    .map(item -> {
                        WorkbenchVO.WorkbenchRecordItemVO itemVo = new WorkbenchVO.WorkbenchRecordItemVO();
                        itemVo.setId(item.getId());
                        itemVo.setPointId(item.getPointId());
                        itemVo.setPointName(item.getPointName());
                        itemVo.setArea(item.getArea());
                        itemVo.setCheckTime(item.getCheckTime());
                        itemVo.setLongitude(item.getLongitude());
                        itemVo.setLatitude(item.getLatitude());
                        itemVo.setCheckStatus(item.getCheckStatus());
                        itemVo.setRemark(item.getRemark());
                        itemVo.setImgUrl(item.getImgUrl());
                        itemVo.setDeviceInfo(item.getDeviceInfo());
                        return itemVo;
                    }).collect(Collectors.toList());
            vo.setRecentList(recentList);
        }

        vo.setStreakDays(calcStreakDays(userId, today));
        return vo;
    }

    private Integer calcStreakDays(Long userId, LocalDate today) {
        int count = 0;
        LocalDate curr = today;
        while (true) {
            boolean hasRecord = workbenchMapper.existsUserRecordByDate(userId, curr);
            if (!hasRecord) {
                break;
            }
            count++;
            curr = curr.minusDays(1);
            if (count >= 90) {
                break;
            }
        }
        return count;
    }
}
