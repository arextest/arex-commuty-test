package com.arextest.agent.test.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.arextest.agent.test.util.ResultUtil;

/**
 * after login failed
 * @author daixq
 * @date 2023/01/17
 */
@Slf4j
@Component
public class UserLoginFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception){
        log.info("[login failed]");
        if (exception instanceof UsernameNotFoundException){
            log.info("[login failed]"+exception.getMessage());
            ResultUtil.responseJson(response,ResultUtil.resultCode(500,"user does not exist"));
        }
        if (exception instanceof LockedException){
            log.info("[login failed]"+exception.getMessage());
            ResultUtil.responseJson(response,ResultUtil.resultCode(500,"user is frozen"));
        }
        if (exception instanceof BadCredentialsException){
            log.info("[login failed]"+exception.getMessage());
            ResultUtil.responseJson(response,ResultUtil.resultCode(500,"username or password is wrong"));
        }
        ResultUtil.responseJson(response,ResultUtil.resultCode(500,"login failed"));
    }
}
