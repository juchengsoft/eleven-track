package com.eleven.track.dto;

import lombok.Data;

@Data
public class ParkingApplyQueryDTO {
    private Long pageNum;
    private Long pageSize;
    private String keyword;
    private String houseNo;
    private String beginTime;
    private String endTime;
}
