package com.ll.vhr.server.service;

import com.ll.vhr.server.domain.Menu;
import com.ll.vhr.server.domain.ResultBean;

import java.util.List;

public interface MenuService {
    public ResultBean<List<Menu>> getMenusByHrId();

    public ResultBean<List<Menu>> getAllMenusWithRole();

    public ResultBean<List<Menu>> getAllMenus();

    public ResultBean<List<Menu>> getMidsByRid(Integer rid);

    public boolean updateMenuRole(Integer rid, Integer[] mids);

}
