package com.eleven.track.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("got_record")
public class GotRecord {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long pointId;
    private String area;
    private Long userId;
    private LocalDateTime checkTime;
    private BigDecimal longitude;
    private BigDecimal latitude;
    private Integer checkStatus;
    private String remark;
    private String imgUrl;
    private String deviceInfo;
    private  Double distanceMeter;
}