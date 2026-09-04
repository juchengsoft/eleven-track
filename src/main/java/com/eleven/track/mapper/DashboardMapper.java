package com.eleven.track.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eleven.track.entity.GotRecord;
import com.eleven.track.vo.DashboardVO;
import com.eleven.track.vo.DayCountVO;
import com.eleven.track.vo.UserCheckCountVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DashboardMapper extends BaseMapper<GotRecord> {

    DashboardVO selectDashboardCard(@Param("queryDate") String queryDate);

    List<DayCountVO> selectExistDayCount(@Param("queryDate") String queryDate);

    List<UserCheckCountVO> selectUserTodayCheckCount(@Param("queryDate") String queryDate);
}
