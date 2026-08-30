package com.eleven.track.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PointStatusDTO {
    @NotNull
    private Long id;
    @NotNull
    private Integer status;
}
