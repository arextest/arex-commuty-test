package com.arextest.agent.test.mapper;

import com.arextest.agent.test.entity.Role;
import com.arextest.agent.test.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;

/**
 * @author daixq
 * @date 2023/01/12
 */
public interface UserMapper extends BaseMapper<User> {
    List<Role> findRoles(Integer userId);
}
