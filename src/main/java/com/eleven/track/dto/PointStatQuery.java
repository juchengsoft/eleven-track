package com.eleven.track.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class PointStatQuery {
    private Long current = 1L;
    private Long size = 10L;
    private String pointId;
    private LocalDate startDate;
    private LocalDate endDate;
    private String checkLevel;
}
