//package com.ll.vhr.server.filter;
//
//import com.alibaba.fastjson.JSONObject;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.ll.vhr.server.config.RsaKeyProperties;
//import com.ll.vhr.server.domain.Hr;
//import com.ll.vhr.server.domain.Role;
//import com.ll.vhr.server.util.JwtUtil;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * 认证过滤器
// */
//public class JwtLoginFilter extends UsernamePasswordAuthenticationFilter {
//    private AuthenticationManager authenticationManager;
//    private RsaKeyProperties rsaKeyProperties;
//
//    public JwtLoginFilter(AuthenticationManager authenticationManager, RsaKeyProperties rsaKeyProperties) {
//        this.authenticationManager = authenticationManager;
//        this.rsaKeyProperties = rsaKeyProperties;
//    }
//
//    /**
//     * 这个方法用来去尝试验证用户的，父类中是从POST请求的form表单中获取，但是这里不是，所以重写
//     */
//    @Override
//    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
//        try {
//            Hr hr = JSONObject.parseObject(request.getInputStream(), Hr.class);
//            return authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(hr.getUsername(), hr.getPassword()));
//        } catch (Exception e) {
//            try {
//                response.setContentType("application/json;charset=utf-8");
//                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//                PrintWriter out = response.getWriter();
//                Map<String, Object> map = new HashMap<>();
//                map.put("code", HttpServletResponse.SC_UNAUTHORIZED);
//                map.put("message", "账户或密码错误！");
//                out.write(new ObjectMapper().writeValueAsString(map));
//                out.flush();
//                out.close();
//
//            } catch (Exception e1) {
//                e1.printStackTrace();
//            }
//            throw new RuntimeException(e);
//        }
//
//    }
//
//
//    @Override
//    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
//        Hr hr = new Hr();
//        hr.setUsername(authResult.getName());
//        hr.setRoles((List<Role>) authResult.getAuthorities());
//        String token = JwtUtil.generateTokenExpireInMinutes(hr, rsaKeyProperties.getPrivateKey(), 60);
//        response.addHeader("accessToken", token);
//        try {
//            //登录成功时，返回json格式进行提示
//            response.setContentType("application/json;charset=utf-8");
//            response.setStatus(HttpServletResponse.SC_OK);
//            PrintWriter out = response.getWriter();
//            Map<String, Object> map = new HashMap<>();
//            map.put("code", HttpServletResponse.SC_OK);
//            map.put("message", "登录成功！");
//            out.write(new ObjectMapper().writeValueAsString(map));
//            out.flush();
//            out.close();
//
//        } catch (Exception e1) {
//            e1.printStackTrace();
//        }
//    }
//}
