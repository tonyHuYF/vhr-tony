package com.ll.vhr.server.aspect;

import cn.hutool.core.util.RandomUtil;
import com.ll.vhr.server.domain.CommonException;
import com.ll.vhr.server.domain.Error;
import com.ll.vhr.server.domain.ResultBean;
import com.ll.vhr.server.util.ExceptionUtil;
import com.ll.vhr.server.util.RequestUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;


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

    /**
     * 拦截捕获自定义异常
     */
    @ExceptionHandler(value = CommonException.class)
    public ResultBean errorHandler(CommonException ex) {
        String prefix = RandomUtil.randomString(10);
        String errorMessage = "\r\n业务错误代号:" + prefix + ", code:" + ex.getError().getCode() + ", message:" + ex.getError().getMessage();
        StringBuilder sb = new StringBuilder();
        RequestUtil.getHttpParamLogInfo(sb);
        log.warn(errorMessage + sb.toString());
        if (ex.getExtra() != null) {
            prefix = ex.getExtra().toString();
        }
        return new ResultBean<>(ex, prefix);
    }

    /**
     * 解决SpringMVC 接收List/数组大小默认限制为256个
     */
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setAutoGrowNestedPaths(true);
        binder.setAutoGrowCollectionLimit(Integer.MAX_VALUE);
    }


}
