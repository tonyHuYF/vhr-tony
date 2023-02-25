package com.ll.vhr.server.filter;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.ll.vhr.server.config.RsaKeyProperties;
import com.ll.vhr.server.domain.Hr;
import com.ll.vhr.server.domain.Payload;
import com.ll.vhr.server.util.JwtUtil;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    private RsaKeyProperties rsaKeyProperties;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    public JwtAuthenticationTokenFilter(RsaKeyProperties rsaKeyProperties) {
        this.rsaKeyProperties = rsaKeyProperties;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //获取token
        String token = request.getHeader("accessToken");

        if (token == null) {
            //放行
            filterChain.doFilter(request, response);
            return;
        }


        //解析jwt token
        Payload<Hr> payload = JwtUtil.getInfoFromToken(token, rsaKeyProperties.getPublicKey(), Hr.class);

        if (ObjectUtil.isEmpty(payload)) {
            throw new RuntimeException("token非法");
        }


        String userId = payload.getUserInfo().getId().toString();

        //从redis上获取user信息
        String userInfo = stringRedisTemplate.opsForValue().get("login:" + userId);

        if (ObjectUtil.isEmpty(userInfo)) {
            throw new RuntimeException("用户未登录");
        }

        //转换格式
        Hr hr = JSONUtil.toBean(userInfo, Hr.class);

        //塞到SecurityContextHolder里
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(hr, null, hr.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        //放行
        filterChain.doFilter(request, response);
    }
}
