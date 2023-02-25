package com.ll.vhr.server.controller;

import com.ll.vhr.server.domain.Hr;
import com.ll.vhr.server.domain.ResultBean;
import com.ll.vhr.server.service.LoginService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class LoginController {

    @Resource
    private LoginService loginService;

    @PostMapping("/login")
    public ResultBean<Map<String, String>> login(@RequestBody Hr hr) {
        Map<String, String> login = loginService.login(hr);
        return new ResultBean<>(login);
    }


    @GetMapping("/logout")
    public ResultBean<Void> logout() {
        loginService.logout();
        return new ResultBean<>();
    }
}
