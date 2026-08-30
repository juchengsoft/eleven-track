package com.eleven.track.vo;

import lombok.Data;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.util.List;

@Data
public class WorkbenchVO {
    private Integer todayTotal;
    private Integer todayChecked;
    private Integer streakDays;
    private List<WorkbenchRecordItemVO> recentList;

    @Data
    public static class WorkbenchRecordItemVO {
        private Long id;
        private String pointId;
        private String pointName;
        private String area;
        private LocalDateTime checkTime;
        private BigDecimal longitude;
        private BigDecimal latitude;
        private Integer checkStatus;
        private String remark;
        private String imgUrl;
        private String deviceInfo;
    }
}
