package com.ll.vhr.server.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import com.ll.vhr.server.config.RsaKeyProperties;
import com.ll.vhr.server.domain.Hr;
import com.ll.vhr.server.service.LoginService;
import com.ll.vhr.server.util.JwtUtil;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class LoginServiceImpl implements LoginService {
    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private StringRedisTemplate stringRedisTemplate;


    @Resource
    private RsaKeyProperties rsaKeyProperties;


    @Override
    public Map<String, String> login(Hr hr) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(hr.getUsername(), hr.getPassword(), null);
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);

        if (ObjectUtil.isNull(authenticate)) {
            throw new RuntimeException("登录失败");
        }

        Hr user = (Hr) authenticate.getPrincipal();
        String userId = user.getId().toString();

        //生成token
        String token = JwtUtil.generateTokenExpireInMinutes(user, rsaKeyProperties.getPrivateKey(), 60);

        Map<String, String> map = new HashMap<>();
        map.put("accessToken", token);

        stringRedisTemplate.opsForValue().set("login:" + userId, JSONObject.toJSONString(user), 60, TimeUnit.MINUTES);

        return map;
    }

    @Override
    public void logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = ((Hr) (authentication.getPrincipal())).getId().toString();

        stringRedisTemplate.delete("login:" + userId);
    }
}
