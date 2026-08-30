package com.eleven.track.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.eleven.track.dto.*;
import com.eleven.track.entity.GotUser;
import com.eleven.track.service.UserService;
import com.eleven.track.vo.ResultVo;
import com.eleven.track.vo.SysUserVO;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Resource
    private UserService userService;

    @GetMapping("/list")
    public ResultVo<IPage<SysUserVO>> list(SysUserQueryDTO dto){
        long current = dto.getPageNum() == null ? 1L : dto.getPageNum();
        long size = dto.getPageSize() == null ? 10L : dto.getPageSize();
        IPage<SysUserVO> page = userService.getUserPage(dto, current, size);
        return ResultVo.success(page);
    }

    @PostMapping("/add")
    public ResultVo<Void> add(@Valid @RequestBody SysUserSaveDTO dto){
        userService.addUser(dto);
        return ResultVo.success();
    }

    @PutMapping("/update")
    public ResultVo<Void> update(@Valid @RequestBody SysUserSaveDTO dto){
        userService.updateUser(dto);
        return ResultVo.success();
    }

    @PutMapping("/status")
    public ResultVo<?> changeStatus(@Valid @RequestBody SysUserStatusDTO dto){
        userService.changeStatus(dto);
        return ResultVo.success();
    }

    @PutMapping("/resetPwd")
    public ResultVo<?> resetPwd(@Valid @RequestBody ResetPwdDTO dto){
        userService.resetPassword(dto);
        return ResultVo.success();
    }

    @DeleteMapping("/{id}")
    public ResultVo<?> delete(@PathVariable Long id){
        userService.removeById(id);
        return ResultVo.success();
    }

    @GetMapping("/profile")
    public ResultVo<?> profile(){
        GotUser user = userService.getCurrentProfile();
        SysUserVO vo = new SysUserVO();
        BeanUtils.copyProperties(user,vo);
        return ResultVo.success(vo);
    }

    @PutMapping("/password")
    public ResultVo<?> updatePassword(@Valid @RequestBody UserPasswordDTO dto){
        userService.updatePassword(dto);
        return ResultVo.success();
    }

    @PutMapping("/profile")
    public ResultVo<SysUserVO> updateProfile(@Valid @RequestBody UpdateProfileDTO dto) {
        userService.updateProfile(dto);
        GotUser user = userService.getCurrentProfile();
        SysUserVO vo = new SysUserVO();
        BeanUtils.copyProperties(user, vo);
        return ResultVo.success(vo);
    }
}
