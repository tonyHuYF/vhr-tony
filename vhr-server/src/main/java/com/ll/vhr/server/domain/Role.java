package com.ll.vhr.server.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

/**
 * 权限信息
 */

@Data
@TableName("role")
public class Role implements GrantedAuthority {

    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 名称(英文)
     */
    private String name;

    /**
     * 名称(中文)
     */
    private String nameZh;

    /**
     * 如果授予的权限可以当作一个String的话，就可以返回一个String
     */
    @Override
    @JsonIgnore
    public String getAuthority() {
        return name;
    }
}
