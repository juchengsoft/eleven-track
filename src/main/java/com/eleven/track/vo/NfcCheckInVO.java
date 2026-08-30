package com.eleven.track.vo;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class NfcCheckInVO {
    private boolean success;
    private String msg;
    private String pointName;
    private String area;
    private String address;
    private LocalDateTime checkTime;
    private Double distanceMeter;
}
