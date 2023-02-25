package com.ll.vhr.server.config;

import com.ll.vhr.server.filter.JwtAuthenticationTokenFilter;
import com.ll.vhr.server.service.HrService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

@Configuration
//@EnableWebSecurity     //加了这个注解才能写SpringSecurity相关的配置
@EnableGlobalMethodSecurity(prePostEnabled = true)  //开启权限控制的注解支持,prePostEnabled表示SpringSecurity内部的权限控制注解开关
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final HrService hrService;

    private final RsaKeyProperties rsaKeyProperties;

    @Resource
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    public WebSecurityConfig(HrService hrService, RsaKeyProperties rsaKeyProperties) {
        this.hrService = hrService;
        this.rsaKeyProperties = rsaKeyProperties;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * 配置SpringSecurity相关信息
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable() //关闭csrf
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //禁用session
                .and()
                .authorizeRequests()
                .antMatchers("/user/login").anonymous()
                .anyRequest()   //其他资源
                .authenticated();     //表示其他资源认证通过后

        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }


    //    /**
//     * 认证用户的来源
//     */
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        //数据库中
//        auth.userDetailsService(hrService).passwordEncoder(passwordEncoder());
//    }

//    /**
//     * 配置SpringSecurity相关信息
//     */
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable() //关闭csrf
//                .authorizeRequests()
//                .antMatchers("/**").hasAnyRole("admin") //角色信息
//                .anyRequest()   //其他资源
//                .authenticated()     //表示其他资源认证通过后
//                .and()
//                .addFilter(new JwtLoginFilter(super.authenticationManager(), rsaKeyProperties))
//                .addFilter(new JwtVerifyFilter(super.authenticationManager(), rsaKeyProperties))
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); //禁用session
//
//
//    }
}
