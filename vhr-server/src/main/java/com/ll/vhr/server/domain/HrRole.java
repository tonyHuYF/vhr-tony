package com.ll.vhr.server.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户_权限关联信息
 */

@Data
@TableName("hr_role")
public class HrRole implements Serializable {

    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * hr的id
     */
    private Integer hrid;

    /**
     * role的id
     */
    private Integer rid;

}
