package com.ll.vhr.server.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@TableName("department")
public class Department implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 部门名称
     */
    private String name;

    /**
     * 父id
     */
    private Integer parentId;

    private String depPath;

    /**
     * 是否启用，1：启用，0：禁用
     */
    private Integer enabled;

    /**
     * 是否父节点，1：是，0：否
     */
    private Integer isParent;

    /**
     * 子部门
     */
    @TableField(exist = false)
    private List<Department> children = new ArrayList<>();

}
