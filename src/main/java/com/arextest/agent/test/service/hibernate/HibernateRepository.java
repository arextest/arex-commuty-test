package com.arextest.agent.test.service.hibernate;

import com.arextest.agent.test.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author daixq
 * @date 2022/11/07
 */
@Repository
public interface HibernateRepository extends JpaRepository<User,Integer> {
}
