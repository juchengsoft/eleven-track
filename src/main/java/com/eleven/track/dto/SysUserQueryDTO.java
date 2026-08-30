package com.eleven.track.dto;

import lombok.Data;

@Data
public class SysUserQueryDTO {
    private Long pageNum;
    private Long pageSize;
    private String keyword;
    private Integer role;
    private Integer status;
}
