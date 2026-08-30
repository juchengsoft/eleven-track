package com.eleven.track.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("got_point")
public class GotPoint {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String pointId;
    private String pointName;
    private String area;
    private BigDecimal longitude;
    private BigDecimal latitude;
    private String address;
    private String nfcLink;
    private String userId;
    private Integer status;
    private String remark;
    private Integer sort;
    private Integer deleted;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}