package com.ll.vhr.server.controller.config;

import com.ll.vhr.server.domain.Menu;
import com.ll.vhr.server.domain.ResultBean;
import com.ll.vhr.server.service.MenuService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 查询menu相关信息
 */

@RestController
@RequestMapping("/system/config")
public class SystemConfigController {

    @Resource
    private MenuService menuService;

    @GetMapping("/menu")
    @PreAuthorize("hasAuthority('ROLE_admin')")
    public ResultBean<List<Menu>> getMenuByHrId() {
        List<Menu> menus = menuService.getMenusByHrId();
        return new ResultBean<>(menus);
    }

}
