package com.ll.vhr.server.aspect;

import cn.hutool.core.util.RandomUtil;
import com.ll.vhr.server.domain.Error;
import com.ll.vhr.server.domain.ResultBean;
import com.ll.vhr.server.util.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@Slf4j
@RestControllerAdvice
public class ExceptionControllerAdvice {

    /**
     * 全局异常捕获处理
     */
    @ExceptionHandler(value = Exception.class)
    public ResultBean errorHandler(Exception ex) {
        String prefix = RandomUtil.randomString(10);
        log.error(ExceptionUtil.getErrorInfoFromException(ex, prefix));
        return new ResultBean<>(Error.internal_server_error, prefix);

    }
}
