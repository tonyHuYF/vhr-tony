package com.ll.vhr.server.service.impl;

import com.ll.vhr.server.domain.Role;
import com.ll.vhr.server.domain.dto.MenuRoleRel;
import com.ll.vhr.server.mapper.RoleMapper;
import com.ll.vhr.server.service.RoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Resource
    private RoleMapper roleMapper;

    @Override
    public List<Role> getAllRoles() {
        return roleMapper.selectList(null);
    }

    @Override
    public Integer addRole(Role role) {
        if (!role.getName().startsWith("ROLE_")) {
            role.setName("ROLE_" + role.getName());
        }
        return roleMapper.insert(role);
    }

    @Override
    public Integer deleteRoleById(Integer rid) {
        return roleMapper.deleteById(rid);
    }

    @Override
    public List<Role> getRolesByMId(Integer mid) {
        return roleMapper.getRolesByMId(mid);
    }

    @Override
    public List<MenuRoleRel> getAllMenuRoleRel() {
        return roleMapper.getAllMenuRoleRel();
    }
}
