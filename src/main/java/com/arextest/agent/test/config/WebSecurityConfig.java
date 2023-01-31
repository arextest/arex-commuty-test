package com.arextest.agent.test.config;

import com.arextest.agent.test.handler.*;
import com.arextest.agent.test.security.JWTAuthenticationTokenFilter;
import com.arextest.agent.test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author daixq
 * @date 2023/01/12
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserService userService;

    @Autowired
    private UserAuthenticationEntryPointHandler userAuthenticationEntryPointHandler;

    @Autowired
    private UserLoginSuccessHandler userLoginSuccessHandler;

    @Autowired
    private UserLoginFailureHandler userLoginFailureHandler;

    @Autowired
    private UserLogoutSuccessHandler userLogoutSuccessHandler;

    @Autowired
    private UserAuthAccessDeniedHandler userAuthAccessDeniedHandler;

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(10);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userService);
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.authorizeRequests()
                .antMatchers("/admin/**")
                .hasRole("admin")
                .antMatchers("/user/**")
                .access("hasAnyRole('admin','user')")
                .antMatchers("/db/**")
                .access("hasRole('dba') and hasRole('admin')")
                .and()
                .exceptionHandling().authenticationEntryPoint(userAuthenticationEntryPointHandler).and()
                .exceptionHandling().accessDeniedHandler(userAuthAccessDeniedHandler).and()
                .formLogin()
                .loginProcessingUrl("/login")
                .successHandler(userLoginSuccessHandler)
                .failureHandler(userLoginFailureHandler)
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .clearAuthentication(true)
                .invalidateHttpSession(true)
                .deleteCookies("Authentication")
                .logoutSuccessHandler(userLogoutSuccessHandler)
                .and()
                .csrf()
                .disable();
        httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        httpSecurity.headers().cacheControl();
        httpSecurity.addFilter(new JWTAuthenticationTokenFilter(authenticationManager()));
    }
}
