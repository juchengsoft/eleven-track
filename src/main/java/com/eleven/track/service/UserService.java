package com.eleven.track.service;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eleven.track.dto.*;
import com.eleven.track.entity.GotUser;
import com.eleven.track.mapper.GotUserMapper;
import com.eleven.track.vo.SelectVO;
import com.eleven.track.vo.SysUserVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService extends ServiceImpl<GotUserMapper, GotUser> {

    private final GotUserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public GotUser getByUsername(String username) {
        return userMapper.selectByUsername(username);
    }

    public List<SelectVO> getUserSelect() {
        return userMapper.selectList(null).stream()
                .map(u -> new SelectVO(u.getId(), u.getNickName()))
                .collect(Collectors.toList());
    }

    public IPage<SysUserVO> getUserPage(SysUserQueryDTO dto, long current, long size) {
        Page<GotUser> page = new Page<>(current, size);
        LambdaQueryWrapper<GotUser> wrapper = new LambdaQueryWrapper<>();

        if (StringUtils.hasText(dto.getKeyword())) {
            wrapper.and(w -> w.like(GotUser::getUsername, dto.getKeyword())
                    .or().like(GotUser::getNickName, dto.getKeyword())
                    .or().like(GotUser::getPhone, dto.getKeyword()));
        }
        if (dto.getRole() != null) {
            wrapper.eq(GotUser::getRole, dto.getRole());
        }
        if (dto.getStatus() != null) {
            wrapper.eq(GotUser::getStatus, dto.getStatus());
        }
        wrapper.orderByDesc(GotUser::getCreateTime);

        Page<GotUser> userPage = baseMapper.selectPage(page, wrapper);
        return userPage.convert(source -> {
            SysUserVO vo = new SysUserVO();
            BeanUtils.copyProperties(source, vo);
            return vo;
        });
    }

    public void addUser(SysUserSaveDTO dto) {
        long count = count(new LambdaQueryWrapper<GotUser>()
                .eq(GotUser::getUsername, dto.getUsername()));
        if (count > 0) {
            throw new RuntimeException("登录账号已存在");
        }
        GotUser user = new GotUser();
        BeanUtils.copyProperties(dto, user);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        save(user);
    }


    public void updateUser(SysUserSaveDTO dto) {
        GotUser dbUser = getById(dto.getId());
        if (dbUser == null) {
            throw new RuntimeException("用户不存在");
        }
        GotUser user = new GotUser();
        BeanUtils.copyProperties(dto, user);
        user.setPassword(null);
        updateById(user);
    }

    public void changeStatus(SysUserStatusDTO dto) {
        GotUser user = new GotUser();
        user.setId(dto.getId());
        user.setStatus(dto.getStatus());
        updateById(user);
    }

    public void resetPassword(ResetPwdDTO dto) {
        GotUser user = getById(dto.getUserId());
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        updateById(user);
    }

    public GotUser getCurrentProfile() {
        Long userId = StpUtil.getLoginIdAsLong();
        return getById(userId);
    }

    public void updatePassword(UserPasswordDTO dto) {
        Long userId = StpUtil.getLoginIdAsLong();
        GotUser user = getById(userId);
        if(user == null){
            throw new RuntimeException("用户不存在");
        }
        boolean match = passwordEncoder.matches(dto.getOldPassword(), user.getPassword());
        if(!match){
            throw new RuntimeException("当前密码输入错误");
        }
        if(dto.getOldPassword().equals(dto.getNewPassword())){
            throw new RuntimeException("新密码不能与旧密码相同");
        }
        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        updateById(user);
    }

    public void updateProfile(UpdateProfileDTO dto) {
        Long userId = StpUtil.getLoginIdAsLong();
        GotUser user = getById(userId);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        user.setNickName(dto.getNickName());
        user.setPhone(dto.getPhone());
        user.setRemark(dto.getRemark());
        updateById(user);
    }
}