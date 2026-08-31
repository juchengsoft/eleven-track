package com.eleven.track.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("got_user")
public class GotUser {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long depId;
    private String username;
    private String password;
    private String nickName;
    private String phone;
    private Integer role;
    private Integer status;
    private Integer deleted;
    private String remark;
    private LocalDateTime createTime;
    private LocalDateTime lastLoginTime;
}
