package com.ll.vhr.server.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ll.vhr.server.domain.Menu;
import com.ll.vhr.server.domain.ResultBean;
import com.ll.vhr.server.mapper.MenuMapper;
import com.ll.vhr.server.service.MenuService;
import com.ll.vhr.server.service.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class MenuServiceImpl implements MenuService {

    @Resource
    private MenuMapper menuMapper;

    @Resource
    private RoleService roleService;

    @Override
    public ResultBean<List<Menu>> getMenusByHrId() {
        //todo 先写死hid，之后替换
        Integer hrid = 13;
        List<Menu> hrMenus = menuMapper.getMenusByHrId(hrid);

        //查询菜单
        List<Menu> menus = menuMapper.selectList(new LambdaQueryWrapper<Menu>().eq(Menu::getEnabled, 1));

        //为“所有”的父id补0
        Map<Integer, List<Menu>> menusMap = menus.stream().collect(Collectors.groupingBy(
                k -> k.getParentId() == null ? 0 : k.getParentId()));

        //补全子菜单、对应权限信息
        hrMenus.forEach(p -> {
            //子菜单
            if (ObjectUtil.isNotEmpty(menusMap.get(p.getId()))) {
                p.setChildren(menusMap.get(p.getId()));
            }
        });

        return new ResultBean<>(hrMenus);
    }

    @Override
    public ResultBean<List<Menu>> getAllMenusWithRole() {
        return null;
    }

    @Override
    public ResultBean<List<Menu>> getAllMenus() {
        return null;
    }

    @Override
    public ResultBean<List<Menu>> getMidsByRid(Integer rid) {
        return null;
    }

    @Override
    public boolean updateMenuRole(Integer rid, Integer[] mids) {
        return false;
    }
}
