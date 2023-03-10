package com.ll.vhr.server.service;

import com.ll.vhr.server.domain.Menu;

import java.util.List;

public interface MenuService {
    public List<Menu> getMenusByHrId();

    public List<Menu> getMenusByRoleIds(Integer[] rids);

    public List<Menu> getAllMenusWithRole();

    public List<Menu> getAllMenus();

    public List<Integer> getMidsByRid(Integer rid);

    public boolean updateMenuRole(Integer rid, Integer[] mids);

}
