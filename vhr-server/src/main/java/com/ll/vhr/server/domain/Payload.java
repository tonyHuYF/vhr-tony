package com.ll.vhr.server.domain;

import lombok.Data;

import java.util.Date;

/**
 * 为了方便后期获取token中的用户信息，为token中载荷部分单独封装成一个对象
 */

@Data
public class Payload<T> {
    /**
     * id
     */
    private String id;
    /**
     * 用户信息
     */
    private T userInfo;
    /**
     * 过期时间
     */
    private Date expiration;
}
