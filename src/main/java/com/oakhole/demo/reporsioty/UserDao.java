package com.oakhole.demo.reporsioty;

import com.oakhole.demo.entity.AppUser;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * @author Administrator
 * @since 14-2-17
 */
public interface UserDao extends PagingAndSortingRepository<AppUser, Long>, JpaSpecificationExecutor<AppUser> {

    AppUser findByRealName(String realName);

    AppUser findByUsername(String username);
}
