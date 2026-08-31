package com.eleven.track.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class NfcCheckInDTO {
    private Long depId;
    private String pointId;
    private BigDecimal longitude;
    private BigDecimal latitude;
    private String deviceInfo;
    private String remark;
}
