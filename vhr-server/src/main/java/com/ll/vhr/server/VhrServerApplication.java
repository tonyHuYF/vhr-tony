package com.ll.vhr.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableCaching //开启缓存注解
public class VhrServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(VhrServerApplication.class, args);
    }

}
