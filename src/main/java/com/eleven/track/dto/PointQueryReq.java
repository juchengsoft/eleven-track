package com.eleven.track.dto;

import lombok.Data;

@Data
public class PointQueryReq {
    private String pointName;
    private String area;
    private Long responsibleUid;
    private Integer status;
    private Long current = 1L;
    private Long size = 10L;
}
