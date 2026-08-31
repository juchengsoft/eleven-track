package com.eleven.track.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class SysUserSaveDTO {
    private Long id;
    private Long depId;

    @NotBlank(message = "登录账号不能为空")
    private String username;

    private String password;

    @NotBlank(message = "姓名/昵称不能为空")
    private String nickName;

    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式错误")
    private String phone;

    @NotNull(message = "请选择角色")
    private Integer role;

    @NotNull(message = "状态不能为空")
    private Integer status;
}
