package com.arextest.agent.test.util;

import com.arextest.agent.test.config.JWTConfig;
import com.arextest.agent.test.entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
/**
 * create JWT after login successfully
 * @author daixq
 * @date 2023/01/17
 */
public class JwtTokenUtil {
    public static String createAccessToken(User user) {
        return Jwts.builder()
                .setId(user.getId()+"")
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .setIssuer("trip")
                .claim("authorities", JsonUtil.toJson(user.getAuthorities()))
                .setExpiration(new Date(System.currentTimeMillis() + JWTConfig.expiration))
                .signWith(SignatureAlgorithm.HS512, JWTConfig.secret)
                .compact();
    }
}
