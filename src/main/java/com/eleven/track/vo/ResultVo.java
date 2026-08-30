package com.eleven.track.vo;

import lombok.Data;

@Data
public class ResultVo<T> {
    private Integer code;
    private String msg;
    private T data;

    public static <T> ResultVo<T> success(T data) {
        ResultVo<T> vo = new ResultVo<>();
        vo.setCode(200);
        vo.setMsg("操作成功");
        vo.setData(data);
        return vo;
    }

    public static <T> ResultVo<T> success() {
        return success(null);
    }

    public static <T> ResultVo<T> fail(String msg) {
        ResultVo<T> vo = new ResultVo<>();
        vo.setCode(500);
        vo.setMsg(msg);
        return vo;
    }

    public static <T> ResultVo<T> fail(Integer code, String msg) {
        ResultVo<T> vo = new ResultVo<>();
        vo.setCode(code);
        vo.setMsg(msg);
        return vo;
    }
}