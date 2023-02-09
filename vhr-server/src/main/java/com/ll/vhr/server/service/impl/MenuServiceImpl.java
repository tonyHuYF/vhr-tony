package com.ll.vhr.server.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ll.vhr.server.domain.Menu;
import com.ll.vhr.server.domain.Role;
import com.ll.vhr.server.domain.dto.MenuRoleRel;
import com.ll.vhr.server.mapper.MenuMapper;
import com.ll.vhr.server.service.MenuService;
import com.ll.vhr.server.service.RoleService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MenuServiceImpl implements MenuService {

    @Resource
    private MenuMapper menuMapper;

    @Resource
    private RoleService roleService;

    @Override
    public List<Menu> getMenusByHrId() {
        //todo 先写死hid，之后替换
        Integer hrid = 13;
        List<Menu> hrMenus = menuMapper.getMenusByHrId(hrid);

        //查询菜单
        List<Menu> menus = menuMapper.selectList(new LambdaQueryWrapper<Menu>().eq(Menu::getEnabled, 1));

        //补全子菜单信息
        List<Menu> result = hrMenus.stream().peek(p -> p.setChildren(getChildMenu(p, menus))).collect(Collectors.toList());

        return result;
    }

    @Override
    @Cacheable(value = "menu", key = "'getAllMenusWithRole'")
    public List<Menu> getAllMenusWithRole() {
        //查询菜单
        List<Menu> menus = menuMapper.selectList(null);

        //补录权限
        List<MenuRoleRel> allMenuRoleRel = roleService.getAllMenuRoleRel();

        Map<Integer, List<MenuRoleRel>> roleMap = allMenuRoleRel.stream()
                .collect(Collectors.groupingBy(MenuRoleRel::getMid));

        menus.forEach(p -> {
            //补录权限
            List<MenuRoleRel> menuRoleRels = roleMap.get(p.getId());
            if (ObjectUtil.isNotEmpty(menuRoleRels)) {
                p.setRoles(BeanUtil.copyToList(menuRoleRels, Role.class));
            }
        });

        return menus;
    }

    @Override
    @Cacheable(value = "menu", key = "'getAllMenus'")
    public List<Menu> getAllMenus() {
        List<Menu> menus = menuMapper.selectList(null);

        //查询整颗树结构
        List<Menu> list = menus.stream().filter(p -> p.getParentId() == 0)
                .peek(p -> p.setChildren(getChildMenu(p, menus)))
                .collect(Collectors.toList());

        return list;
    }

    /**
     * 获取子menu
     */
    private List<Menu> getChildMenu(Menu menu, List<Menu> list) {
        List<Menu> children = list.stream().filter(p -> p.getParentId().intValue() == menu.getId().intValue())
                .peek(m -> m.setChildren(getChildMenu(m, list)))
                .collect(Collectors.toList());
        return children;
    }

    @Override
    public List<Integer> getMidsByRid(Integer rid) {
        return menuMapper.getMidsByRid(rid);
    }

    @Override
    @Transactional
    @CacheEvict(value = "menu", allEntries = true)
    public boolean updateMenuRole(Integer rid, Integer[] mids) {
        menuMapper.deleteByRid(rid);
        if (ObjectUtil.isEmpty(mids)) {
            return true;
        }
        Integer result = menuMapper.insertRecord(rid, mids);
        return result == mids.length;
    }
}
