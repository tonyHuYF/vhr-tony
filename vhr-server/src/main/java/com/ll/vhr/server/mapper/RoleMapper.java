package com.ll.vhr.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ll.vhr.server.domain.Role;
import com.ll.vhr.server.domain.dto.HrRoleRel;
import com.ll.vhr.server.domain.dto.MenuRoleRel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RoleMapper extends BaseMapper<Role> {

    public List<Role> getRolesByMId(Integer mid);

    public List<MenuRoleRel> getAllMenuRoleRel();

    public List<HrRoleRel>getAllHrRoleRel();
}
