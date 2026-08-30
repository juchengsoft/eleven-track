package com.eleven.track.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eleven.track.entity.GotPoint;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface GotPointMapper extends BaseMapper<GotPoint> {
    GotPoint selectByPointId(@Param("pointId") String pointId);
}
