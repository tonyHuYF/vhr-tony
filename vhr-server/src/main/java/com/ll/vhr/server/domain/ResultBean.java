package com.ll.vhr.server.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 统一返回消息
 */

@SuppressWarnings("unchecked")
@Data
@NoArgsConstructor
public class ResultBean<T> implements Serializable {
    private int code;
    private String message = "success";
    private T data;

    public ResultBean(CommonException ex) {
        this.code = ex.getError().getCode();
        this.message = ex.getError().getMessage();
        this.data = (T) ex.getExtra();

        if (this.data == null) {
            this.data = (T) "";
        }
    }

    public ResultBean(CommonException ex, String prefix) {
        this.code = ex.getError().getCode();
        this.message = ex.getError().getMessage();
        this.data = (T) prefix;
    }

    public ResultBean(T t) {
        if (t instanceof Error) {
            Error e = (Error) t;
            this.code = e.getCode();
            this.message = e.getMessage();
        } else {
            this.data = t;
        }

        if (this.data == null) {
            this.data = (T) "";
        }
    }

    public ResultBean(T t, String prefix) {
        if (t instanceof Error) {
            Error e = (Error) t;
            this.code = e.getCode();
            this.message = e.getMessage();
            this.data = (T) prefix;
        } else {
            this.data = (T) (prefix + ":" + t.toString());
        }
    }


}
