package com.eleven.track.vo;

import lombok.Data;
import java.time.LocalDate;

@Data
public class DayCountVO {
    private LocalDate dt;
    private Integer cnt;
}
