package com.oakhole.demo.service;

import com.oakhole.demo.entity.AppUser;
import com.oakhole.demo.reporsioty.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    /**
     * <pre></pre>
     * set {@code readOnly} just control read data clearly
     * @param id
     * @return
     */
    @Transactional(readOnly = true)
    public AppUser getUser(long id) {
        AppUser appUser = this.userDao.findOne(id);
        return appUser;
    }

    /**
     * <pre>find all users</pre>
     * @return
     */
    public List<AppUser> getAllUsers() {
        List<AppUser> userList = (List<AppUser>) this.userDao.findAll();
        return userList;
    }
}
