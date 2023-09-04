package com.seiryuu.common.domain;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Seiryuu
 * @since 2023-02-09 14:51
 * 系统设置
 */
@Data
@Component
@ConfigurationProperties(prefix = "appconfig")
public class AppConfig {

    /**
     * 令牌秘钥
     */
    private String secret;

    /**
     * 过期时间
     */
    private int expireTime;
}
