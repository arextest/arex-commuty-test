package com.arextest.agent.test.service;

import com.arextest.agent.test.entity.Role;
import com.arextest.agent.test.entity.User;
import com.arextest.agent.test.mapper.UserMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * @author daixq
 * @date 2023/01/12
 */
@Service
public class UserService implements UserDetailsService {
    @Autowired
    UserMapper userMapper;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username",username);
        //根据页面传的用户名查找数据库判断是否存在该用户
        User user = userMapper.selectOne(wrapper);
        if (user==null){
            throw new UsernameNotFoundException("用户不存在");
        }
        List<Role> roles = userMapper.findRoles(user.getId());
//        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
//        //遍历当前用户的角色集合组装权限
//        for (Role role : roles) {
//            authorities.add(new SimpleGrantedAuthority(role.getName()));
//        }
//        return new User(username,user.getPassword(),authorities);//如果用户没有角色会NullPointerException
        user.setRoles(roles);
        return user;
    }
}
