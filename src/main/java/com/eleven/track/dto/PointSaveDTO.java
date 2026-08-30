package com.eleven.track.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class PointSaveDTO {
    private Long id;
    private String pointId;
    @NotBlank(message = "点位名称不能为空")
    private String pointName;
    @NotBlank(message = "所属区域不能为空")
    private String area;
    private BigDecimal longitude;
    private BigDecimal latitude;
    @NotBlank(message = "详细地址不能为空")
    private String address;
    private String nfcLink;
    private String userId;
    private Integer sort;
    private String remark;
}
