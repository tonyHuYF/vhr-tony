package com.ll.vhr.server.domain;

import lombok.Data;

/**
 * 公共异常类
 * 抛出故障时设置errorCode，从Error枚举类获取errorCode
 * Controller层捕获到CommonException后可拿到errorCode、message返回给前台
 */

@Data
public class CommonException extends RuntimeException {
    private Error error;
    private Object extra;

    public CommonException(Error error) {
        super(error.getMessage());
        this.error = error;
    }

    public CommonException(Error error, Object extra) {
        super(error.getMessage());
        this.error = error;
        this.extra = extra;
    }

}
