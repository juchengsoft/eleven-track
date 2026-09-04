package com.eleven.track.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eleven.track.entity.GotRecord;
import com.eleven.track.mapper.DashboardMapper;
import com.eleven.track.vo.DashboardVO;
import com.eleven.track.vo.DayCountVO;
import com.eleven.track.vo.UserCheckCountVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashboardService extends ServiceImpl<DashboardMapper, GotRecord> {

    private final DashboardMapper dashboardMapper;

    public DashboardVO getDashboardStat() {
        DashboardVO vo = dashboardMapper.selectDashboardCard();
        List<DayCountVO> dayList = dashboardMapper.selectExistDayCount();
        List<Integer> trendData = fillLast30Day(dayList);
        vo.setTrendData(trendData);
        List<UserCheckCountVO> userTodayCheckList = dashboardMapper.selectUserTodayCheckCount();
        vo.setUserTodayCheckList(userTodayCheckList);
        return vo;
    }

    private List<Integer> fillLast30Day(List<DayCountVO> dbList) {
        Map<LocalDate, Integer> dateMap = dbList.stream()
                .collect(Collectors.toMap(DayCountVO::getDt, DayCountVO::getCnt));
        List<Integer> result = new ArrayList<>();
        LocalDate now = LocalDate.now();
        for (int i = 29; i >= 0; i--) {
            LocalDate targetDay = now.minusDays(i);
            result.add(dateMap.getOrDefault(targetDay, 0));
        }
        return result;
    }
}
