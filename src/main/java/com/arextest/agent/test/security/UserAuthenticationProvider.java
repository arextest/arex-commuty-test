package com.arextest.agent.test.security;

import com.arextest.agent.test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @author daixq
 * @date 2023/01/17
 */
@Component
public class UserAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private UserService userService;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 获取表单输入中返回的用户名
        String userName = (String) authentication.getPrincipal();
        // 获取表单中输入的密码
        String password = (String) authentication.getCredentials();
        // 查询用户是否存在
        UserDetails userInfo = userService.loadUserByUsername(userName);
        if (userInfo == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        // 我们还要判断密码是否正确，这里我们的密码使用BCryptPasswordEncoder进行加密的
        if (!new BCryptPasswordEncoder().matches(password, userInfo.getPassword())) {
            throw new BadCredentialsException("密码不正确");
        }
//        // 还可以加一些其他信息的判断，比如用户账号已停用等判断
//        if (userInfo.getStatus().equals("PROHIBIT")){
//            throw new LockedException("该用户已被冻结");
//        }
        // 角色集合
//        Set<GrantedAuthority> authorities = new HashSet<>();
//        // 查询用户角色
//        List<SysRoleEntity> sysRoleEntityList = userMapper.findRoles(userInfo.get)
//        for (SysRoleEntity sysRoleEntity: sysRoleEntityList){
//            authorities.add(new SimpleGrantedAuthority("ROLE_" + sysRoleEntity.getRoleName()));
//        }
//        userInfo.setAuthorities(authorities);
        // 进行登录
        return new UsernamePasswordAuthenticationToken(userInfo, password, userInfo.getAuthorities());
    }
    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
