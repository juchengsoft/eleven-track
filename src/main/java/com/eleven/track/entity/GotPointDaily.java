package com.eleven.track.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("got_point_daily")
public class GotPointDaily {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long pointId;
    private String pointName;
    private String area;
    private BigDecimal longitude;
    private BigDecimal latitude;
    private String address;
    private Integer pointStatus;
    private LocalDate statDate;
    private Integer checkCount;
    private LocalDateTime lastCheckTime;
    @TableField(exist = false)
    private String checkLevel;

    public void calcCheckLevel() {
        if (this.checkCount == null || this.checkCount == 0) {
            this.checkLevel = "zero";
        } else if (checkCount >= 1 && checkCount <= 2) {
            this.checkLevel = "low";
        } else if (checkCount >= 3 && checkCount <= 5) {
            this.checkLevel = "normal";
        } else {
            this.checkLevel = "high";
        }
    }
}