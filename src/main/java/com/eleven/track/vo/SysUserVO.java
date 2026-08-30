package com.eleven.track.vo;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class SysUserVO {
    private Long id;
    private String username;
    private String nickName;
    private String phone;
    private Integer role;
    private Integer status;
    private String remark;
    private LocalDateTime createTime;
    private LocalDateTime lastLoginTime;
}
