package com.eleven.track.mapper;

import com.eleven.track.vo.WorkbenchSummaryJoinVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.time.LocalDate;
import java.util.List;

@Mapper
public interface WorkbenchMapper {

    List<WorkbenchSummaryJoinVO> queryWorkbenchOnce(@Param("userId") Long userId, @Param("today") LocalDate today);

    boolean existsUserRecordByDate(@Param("userId") Long userId, @Param("date") LocalDate date);
}
