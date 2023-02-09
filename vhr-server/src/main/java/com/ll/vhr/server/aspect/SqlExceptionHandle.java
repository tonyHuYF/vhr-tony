package com.ll.vhr.server.aspect;

import cn.hutool.core.util.RandomUtil;
import com.ll.vhr.server.domain.Error;
import com.ll.vhr.server.domain.ResultBean;
import com.ll.vhr.server.util.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;


@Slf4j
@RestControllerAdvice
@Order(1)
public class SqlExceptionHandle {

    /**
     * 拦截sql错误异常
     */
    @ExceptionHandler(value = SQLException.class)
    public ResultBean errorHandler(SQLException ex) {
        String prefix = RandomUtil.randomString(10);
        log.error(ExceptionUtil.getErrorInfoFromException(ex, prefix));
        if (ex instanceof SQLIntegrityConstraintViolationException) {
            return new ResultBean<>(Error.data_bind_error, prefix);
        }
        return new ResultBean<>(Error.internal_server_error, prefix);
    }

}
