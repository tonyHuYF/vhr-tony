package com.ll.vhr.server.service;

import com.ll.vhr.server.domain.Role;
import com.ll.vhr.server.domain.dto.HrRoleRel;
import com.ll.vhr.server.domain.dto.MenuRoleRel;

import java.util.List;

public interface RoleService {

    public List<Role> getAllRoles();

    public Integer addRole(Role role);

    public Integer deleteRoleById(Integer rid);

    public List<Role> getRolesByMId(Integer mid);

    public List<MenuRoleRel> getAllMenuRoleRel();

    public List<HrRoleRel> getAllHrRoleRel();
}
