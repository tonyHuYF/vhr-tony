package com.ll.vhr.server.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 菜单_权限关联信息
 */

@Data
@TableName("menu_role")
public class MenuRole implements Serializable {

    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * menu的id
     */
    private Integer mid;

    /**
     * role的id
     */
    private Integer rid;

}
