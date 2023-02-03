package com.ll.vhr.server.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 菜单信息
 */

@Data
@TableName("menu")
public class Menu implements Serializable {

    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * url
     */
    private String url;

    /**
     * 路径
     */
    private String path;

    /**
     * 组件
     */
    private String component;

    /**
     * 名称
     */
    private String name;

    /**
     * 图标
     */
    private String iconCls;

    /**
     * 保持在线
     */
    private Integer keepAlive;

    /**
     * 需要auth
     */
    private Integer requireAuth;

    /**
     * 父id
     */
    private Integer parentId;

    /**
     * 是否启用，1：启用，0：禁用，默认：1
     */
    private Integer enabled;

    /**
     * 子菜单
     */
    @TableField(exist = false)
    private List<Menu> children;

    /**
     * 对应权限角色
     */
    @TableField(exist = false)
    private List<Role> roles;
}
