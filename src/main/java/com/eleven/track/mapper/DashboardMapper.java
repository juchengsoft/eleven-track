package com.eleven.track.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eleven.track.entity.GotRecord;
import com.eleven.track.vo.DashboardVO;
import com.eleven.track.vo.DayCountVO;
import com.eleven.track.vo.UserCheckCountVO;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface DashboardMapper extends BaseMapper<GotRecord> {

    DashboardVO selectDashboardCard();

    List<DayCountVO> selectExistDayCount();

    List<UserCheckCountVO> selectUserTodayCheckCount();

}
