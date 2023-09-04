package com.seiryuu.utils;


import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.seiryuu.common.domain.AppConfig;
import org.springframework.stereotype.Component;

import java.util.Calendar;

@Component
public class JwtUtil {
    private static final long MILLIS_SECOND = 1000;

    private static final long MILLIS_MINUTE = 60 * MILLIS_SECOND;

    private static final Long MILLIS_MINUTE_TEN = 20 * 60 * 1000L;

    private static AppConfig appConfig;

    public JwtUtil(AppConfig appConfig) {
        JwtUtil.appConfig = appConfig;
    }

    // 根据用户id与username生成token
    public static String createToken(int id, String account) {
        // 签发时间
        Calendar ins = Calendar.getInstance();
        ins.add(Calendar.SECOND, Convert.toInt(DateUtil.current() + appConfig.getExpireTime() * MILLIS_MINUTE));

        // 秘钥加密方式
        Algorithm algorithm = Algorithm.HMAC256(appConfig.getSecret());

        return JWT.create()
                .withClaim("id", id)
                .withClaim("account", account)
                .withExpiresAt(ins.getTime())
                .sign(algorithm);
    }

    // 获取token信息
    public static DecodedJWT verify(String token) {
        return JWT.require(Algorithm.HMAC256(appConfig.getSecret())).build().verify(token);
    }

}