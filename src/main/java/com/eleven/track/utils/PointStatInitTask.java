package com.eleven.track.utils;

import com.eleven.track.service.PointDailyStatService;
import jakarta.annotation.Resource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class PointStatInitTask {

    @Resource
    private PointDailyStatService pointDailyStatService;

    @Scheduled(cron = "${task.cron}")
    public void runTask(){
        try {
            pointDailyStatService.initTodayPointDaily();
        } catch (Exception ignored) {
        }
    }
}
