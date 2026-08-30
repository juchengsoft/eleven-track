package com.eleven.track.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.eleven.track.entity.GotUser;
import com.eleven.track.service.UserService;
import com.eleven.track.dto.LoginReq;
import com.eleven.track.vo.ResultVo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class LoginController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResultVo<?> login(@RequestBody LoginReq req) {
        GotUser user = userService.getByUsername(req.getUsername());
        if (user == null) {
            return ResultVo.fail("账号或密码错误");
        }
        if (user.getStatus() == 0) {
            return ResultVo.fail("账号已被禁用");
        }
        if (!passwordEncoder.matches(req.getPassword(), user.getPassword())) {
            return ResultVo.fail("账号或密码错误");
        }
        StpUtil.login(user.getId());
        user.setLastLoginTime(LocalDateTime.now());
        userService.updateById(user);
        Map<String, Object> data = new HashMap<>();
        data.put("token", StpUtil.getTokenValue());
        data.put("userId", user.getId());
        data.put("username", user.getUsername());
        data.put("nickName", user.getNickName());
        data.put("role", user.getRole());
        return ResultVo.success(data);
    }

    @PostMapping("/logout")
    public ResultVo<?> logout() {
        StpUtil.logout();
        return ResultVo.success();
    }
}