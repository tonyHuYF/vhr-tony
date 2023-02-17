package com.ll.vhr.server.config;

import com.ll.vhr.server.util.RsaUtil;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.annotation.PostConstruct;
import java.security.PrivateKey;
import java.security.PublicKey;

@Data
@ConfigurationProperties("rsa.key")
public class RsaKeyProperties {
    private String pubKeyPath;
    private String priKeyPath;

    private PublicKey publicKey;
    private PrivateKey privateKey;

    @PostConstruct
    public void init() throws Exception {
        this.publicKey = RsaUtil.getPublicKey(pubKeyPath);
        this.privateKey = RsaUtil.getPrivateKey(priKeyPath);
    }
}
