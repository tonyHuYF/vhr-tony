package com.ll.vhr.server.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ll.vhr.server.config.RsaKeyProperties;
import com.ll.vhr.server.domain.Hr;
import com.ll.vhr.server.domain.Payload;
import com.ll.vhr.server.util.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * 检验token过滤器
 */
public class JwtVerifyFilter extends BasicAuthenticationFilter {

    private RsaKeyProperties rsaKeyProperties;

    public JwtVerifyFilter(AuthenticationManager authenticationManager, RsaKeyProperties rsaKeyProperties) {
        super(authenticationManager);
        this.rsaKeyProperties = rsaKeyProperties;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = request.getHeader("accessToken");

        //没有登录
        if (token == null) {
            chain.doFilter(request, response);
            response.setContentType("application/json;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            PrintWriter out = response.getWriter();
            Map<String, Object> map = new HashMap<>();
            map.put("code", HttpServletResponse.SC_FORBIDDEN);
            map.put("message", "请登录！");
            out.write(new ObjectMapper().writeValueAsString(map));
            out.flush();
            out.close();
            return;
        }

        //登录之后从token中获取用户信息
        Payload<Hr> payload = JwtUtil.getInfoFromToken(token, rsaKeyProperties.getPublicKey(), Hr.class);
        Hr hr = payload.getUserInfo();
        if (hr != null) {
            Authentication authResult = new UsernamePasswordAuthenticationToken(
                    hr.getUsername(), null, hr.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authResult);
            chain.doFilter(request, response);
        }
    }

}
