package com.eleven.track.dto;

import lombok.Data;

@Data
public class ParkingApplyQueryDTO {
    private Long pageNum;
    private Long pageSize;
    private String keyword;
    private Integer applyStatus;
    private String beginTime;
    private String endTime;
}
