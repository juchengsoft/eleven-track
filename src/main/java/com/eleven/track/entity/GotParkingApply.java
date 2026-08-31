package com.eleven.track.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("got_parking_apply")
public class GotParkingApply {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String applyNo;
    private String visitorName;
    private String visitorPhone;
    private String plateNumber;
    private String ownerName;
    private String houseNo;
    private LocalDateTime applyStart;
    private LocalDateTime applyEnd;
    private String remark;
    private LocalDateTime createTime;
    private Integer delFlag;
}
