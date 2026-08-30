package com.eleven.track.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eleven.track.entity.GotUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface GotUserMapper extends BaseMapper<GotUser> {
    @Select("select * from got_user where username = #{username}")
    GotUser selectByUsername(@Param("username") String username);
}