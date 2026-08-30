package com.eleven.track.utils;

import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.eleven.track.entity.GotPointDaily;
import com.eleven.track.entity.GotRecord;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class ExcelUtils {
    public static byte[] exportRecord(List<GotRecord> list) {
        try (ExcelWriter writer = ExcelUtil.getWriter(true)) {
            writer.addHeaderAlias("id", "ID");
            writer.addHeaderAlias("pointId", "点位ID");
            writer.addHeaderAlias("userId", "用户ID");
            writer.addHeaderAlias("checkTime", "打卡时间");
            writer.addHeaderAlias("checkStatus", "打卡状态");
            writer.addHeaderAlias("remark", "备注");
            writer.write(list, true);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            writer.flush(bos, true);
            return bos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("生成Excel失败", e);
        }
    }

    public static byte[] exportPointStat(List<GotPointDaily> list) {
        try (ExcelWriter writer = ExcelUtil.getWriter(true)) {
            writer.addHeaderAlias("pointName", "点位名称");
            writer.addHeaderAlias("area", "所属区域");
            writer.addHeaderAlias("longitude", "经度");
            writer.addHeaderAlias("latitude", "纬度");
            writer.addHeaderAlias("address", "详细地址");
            writer.addHeaderAlias("statDate", "统计日期");
            writer.addHeaderAlias("checkCount", "当日打卡次数");
            writer.addHeaderAlias("lastCheckTime", "最新打卡时间");
            writer.addHeaderAlias("levelText", "活跃度");

            writer.write(list, true);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            writer.flush(bos, true);
            return bos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("生成Excel失败", e);
        }
    }
}
