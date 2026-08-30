package com.eleven.track.vo;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class WorkbenchSummaryJoinVO {
    private Integer todayTotal;
    private Integer todayChecked;

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
