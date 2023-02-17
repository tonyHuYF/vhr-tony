package com.ll.vhr.server.config;

import com.ll.vhr.server.filter.JwtLoginFilter;
import com.ll.vhr.server.filter.JwtVerifyFilter;
import com.ll.vhr.server.service.HrService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity     //加了这个注解才能写SpringSecurity相关的配置
@EnableGlobalMethodSecurity(securedEnabled = true)  //开启权限控制的注解支持,securedEnabled表示SpringSecurity内部的权限控制注解开关
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final HrService hrService;

    private final RsaKeyProperties rsaKeyProperties;

    public WebSecurityConfig(HrService hrService, RsaKeyProperties rsaKeyProperties) {
        this.hrService = hrService;
        this.rsaKeyProperties = rsaKeyProperties;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 认证用户的来源
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //数据库中
        auth.userDetailsService(hrService).passwordEncoder(passwordEncoder());
    }

    /**
     * 配置SpringSecurity相关信息
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable() //关闭csrf
                .authorizeRequests()
                .antMatchers("/**").hasAnyRole("admin") //角色信息
                .anyRequest()   //其他资源
                .authenticated()     //表示其他资源认证通过后
                .and()
                .addFilter(new JwtLoginFilter(super.authenticationManager(), rsaKeyProperties))
                .addFilter(new JwtVerifyFilter(super.authenticationManager(), rsaKeyProperties))
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); //禁用session

















    }
}
