package com.ll.vhr.server.controller.config;

import com.ll.vhr.server.domain.Menu;
import com.ll.vhr.server.domain.ResultBean;
import com.ll.vhr.server.service.MenuService;
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

    @GetMapping("/test")
    public ResultBean getMenuByHrId() {
        ResultBean<List<Menu>> menusByHrId = menuService.getMenusByHrId();

        return menusByHrId;
    }

}
