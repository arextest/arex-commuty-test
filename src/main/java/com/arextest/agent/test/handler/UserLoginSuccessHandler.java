package com.arextest.agent.test.handler;

import com.arextest.agent.test.config.JWTConfig;
import com.arextest.agent.test.entity.User;
import com.arextest.agent.test.util.JwtTokenUtil;
import com.arextest.agent.test.util.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * after login
 * @author daixq
 * @date 2023/01/17
 */
@Slf4j
@Component
public class UserLoginSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication){
        log.info("[login] successfully, create jwt token");
        // create JWT token
        User user = (User) authentication.getPrincipal();
        String token = JwtTokenUtil.createAccessToken(user);
        token = JWTConfig.tokenPrefix + token;
        // response
        Map<String,Object> resultData = new HashMap<>();
        resultData.put("code","200");
        resultData.put("msg", "login successfully");
        resultData.put("token",token);
        ResultUtil.responseJson(response,resultData);
    }
}
