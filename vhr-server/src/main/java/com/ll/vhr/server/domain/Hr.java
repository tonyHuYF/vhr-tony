package com.ll.vhr.server.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户信息
 */

@Data
@TableName("hr")
public class Hr implements UserDetails {

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

    /**
     * 对应菜单权限
     */
    @TableField(exist = false)
    private List<Menu> menus;

    /**
     * 权限--security用
     */
    @TableField(exist = false)
    @JSONField(serialize = false)
    private List<GrantedAuthority> authorities;

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (authorities != null) {
            return authorities;
        }

        authorities = new ArrayList<>();

        List<SimpleGrantedAuthority> roleList = roles.stream()
                .map(p -> new SimpleGrantedAuthority(p.getName())).collect(Collectors.toList());

        List<SimpleGrantedAuthority> menuList = menus.stream()
                .map(p -> new SimpleGrantedAuthority(p.getComponent())).collect(Collectors.toList());

        authorities.addAll(roleList);
        authorities.addAll(menuList);

        return authorities;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return enabled == 1;
    }
}