package com.trench.blog.utils;

import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * jwt 有三部分组成：A.B.C
 * <p>
 * A：Header，{"type":"JWT","alg":"HS256"} 固定
 * B：playload，存放信息，比如，用户id，过期时间等等，可以被解密，不能存放敏感信息
 * C:  签证，A和B加上秘钥 加密而成，只要秘钥不丢失，可以认为是安全的。
 * <p>
 * jwt 验证，主要就是验证C部分 是否合法。
 *
 * @author Trench
 */
public class JWTUtils {
    /**
     * A部分
     */
    private static final String jwtToken = "123456Mszlu!@#$$";

    /**
     * 创建token
     *
     * @param userId
     * @return public static String
     */
    public static String createToken(Long userId) {
        // B部分
        String token;
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        /*
         * .signWith(SignatureAlgorithm.HS256, jwtToken) --> 签发算法，秘钥为jwtToken
         * .setClaims(claims) --> body数据，要唯一，自行设置
         * .setIssuedAt(new Date()) --> 设置签发时间
         * .setExpiration(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 60 * 1000)); --> 一天的有效时间
         */
        JwtBuilder jwtBuilder = Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, jwtToken)
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 60 * 1000));
        token = jwtBuilder.compact();
        return token;
    }

    /**
     * 检测部分
     *
     * @param token
     * @return public static Map<String, Object>
     */
    public static Map<String, Object> checkToken(String token) {
        try {
            Jwt parse = Jwts.parser().setSigningKey(jwtToken).parse(token);
            return (Map<String, Object>) parse.getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * token功能测试 by junit4
     */
    @Test
    public void tokenTest() {
        String token = JWTUtils.createToken(100L);
        System.out.println(token);
        System.out.println(JWTUtils.checkToken(token).get("userId"));
    }
}
