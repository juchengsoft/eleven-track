package com.eleven.track.vo;

import lombok.Data;

@Data
public class UserCheckCountVO {
    private Long userId;
    private String username;
    private String nickName;
    private Long todayCheckCount;
}