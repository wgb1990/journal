package com.oakhole.demo.service;

import com.oakhole.demo.entity.AppUser;
import com.oakhole.demo.reporsioty.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Administrator
 * @since 14-2-17
 */
@Service
@Transactional
public class AppUserService {

    @Autowired
    private UserDao userDao;

    public void saveUser(AppUser appUser) {
        this.userDao.save(appUser);
    }

    public void deleteAppUser(long id) {
        this.userDao.delete(id);
    }

    public AppUser getUser(long id) {
        AppUser appUser = this.userDao.findOne(id);
        return appUser;
    }

}
