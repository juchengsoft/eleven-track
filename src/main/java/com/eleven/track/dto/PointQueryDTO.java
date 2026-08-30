package com.eleven.track.dto;

import lombok.Data;

@Data
public class PointQueryDTO {
    private Integer pageNum;
    private Integer pageSize;
    private String pointName;
    private String area;
    private Integer status;
}
