package com.ll.vhr.server.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 用户信息
 */

@Data
@TableName("hr")
public class Hr implements Serializable {

    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 住宅号码
     */
    private String telephone;

    /**
     * 联系地址
     */
    private String address;

    /**
     * 是否启用，1：启用，0：禁用，默认：1
     */
    private Integer enabled;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 图标
     */
    private String userface;

    /**
     * 备注
     */
    private String remark;

    /**
     * 对应权限角色
     */
    @TableField(exist = false)
    private List<Role> roles;

}
