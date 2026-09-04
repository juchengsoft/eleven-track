package com.eleven.track.vo;

import lombok.Data;
import java.util.List;

@Data
public class DashboardVO {
    private Long totalPoint;
    private Long todayCheck;
    private Long normalUser;
    private Long abnormalCount;
    private List<Integer> trendData;
    private List<UserCheckCountVO> userTodayCheckList;

}
