package com.eleven.track.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("got_department")
public class GotDepartment {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String deptName;
    private String deptCode;
    private Long parentId;
    private Integer sort;
    private Integer status;
    private String remark;
    private Integer deleted;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
