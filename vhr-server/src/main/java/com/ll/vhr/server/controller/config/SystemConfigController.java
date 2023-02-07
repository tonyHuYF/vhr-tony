package com.ll.vhr.server.controller.config;

import com.ll.vhr.server.domain.ResultBean;
import com.ll.vhr.server.service.MenuService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

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
        Integer[] mids = {22,33,44,55};
        boolean b = menuService.updateMenuRole(200, null);

        return new ResultBean<>(b);
    }

}
