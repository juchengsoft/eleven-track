package com.eleven.track.dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import jakarta.validation.constraints.NotNull;

@Data
public class ParkingApplyDTO {
    @NotBlank(message = "访客姓名不能为空")
    private String visitorName;
    @NotBlank(message = "访客手机号不能为空")
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式错误")
    private String visitorPhone;
    @NotBlank(message = "车牌号不能为空")
    private String plateNumber;
    @NotBlank(message = "被访业主姓名不能为空")
    private String ownerName;
    @NotBlank(message = "房号不能为空")
    private String houseNo;
    @NotNull(message = "停车开始时间不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime applyStart;
    @NotNull(message = "停车结束时间不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime applyEnd;
    private String remark;
}
