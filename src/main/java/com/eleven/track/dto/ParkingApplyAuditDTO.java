package com.eleven.track.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ParkingApplyAuditDTO {

    @NotNull(message = "申请id不能为空")
    private Long id;

    @NotNull(message = "审批结果不能为空")
    private Integer auditResult;

    private String auditRemark;
}
