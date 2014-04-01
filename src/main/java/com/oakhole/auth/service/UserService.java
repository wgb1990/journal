package com.oakhole.auth.service;

import com.oakhole.auth.entity.Role;
import com.oakhole.auth.entity.User;
import com.oakhole.auth.repository.RoleDao;
import com.oakhole.auth.repository.UserDao;
import com.oakhole.core.jms.NotifyMessageProducer;
import com.oakhole.core.uitls.*;
import org.apache.shiro.SecurityUtils;
import org.javasimon.aop.Monitored;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 * @since 14-3-7
 */
@SuppressWarnings("ALL")
@Service
@Transactional
@Monitored
public class UserService {

    public static final String HASH_ALGORITHM = "SHA-1";
    public static final int HASH_INTERATIONS = 1024;
    private static final int SALT_SIZE = 8;
    private static Logger logger = LoggerFactory.getLogger(UserService.class);
    @Autowired
    private BusinessLogger businessLogger;
    @Autowired
    private NotifyMessageProducer notifyMessageProducer;
    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;

    public void saveUser(User user) {
        if (StringUtils.isNotBlank(user.getPlainPassword())) {
            encryptPassword(user);
        }
        this.userDao.save(user);
        Map mapData = new HashMap();
        mapData.put("userId", user.getId());
        businessLogger.log("User", "UPDATE", getCurrentUsername(), mapData);
    }

    private String getCurrentUsername() {
        ShiroDbRealm.ShiroUser shiroUser = (ShiroDbRealm.ShiroUser) SecurityUtils.getSubject().getPrincipal();
        return shiroUser.loginName;
    }

    /**
     * 注册新用户
     *
     * @param user
     */
    public void registUser(User user) {
        encryptPassword(user);
        //注册用户时，默认为user，即id=2L的role
        Role role = this.roleDao.findOne(2L);
        user.getRoleList().add(role);
        user.setStatus("enabled");
        this.userDao.save(user);

        //jms 发送消息到邮件
        sendJmsMessage(user);
    }

    /**
     * 发送jms消息
     * @param user
     */
    private void sendJmsMessage(User user) {
        try {
            notifyMessageProducer.sendQueue(user);
        } catch (Exception e) {
            logger.error("发送jms消息错误",e);
        }
    }

    /**
     * 更新用户信息
     *
     * @param user
     */
    public void updateUser(User user) {
        if (StringUtils.isNotBlank(user.getPlainPassword())) {
            encryptPassword(user);
        }
        this.userDao.save(user);

        //发送jms消息通知
        sendJmsMessage(user);
    }

    /**
     * 根据Id值查找user
     *
     * @param id
     * @return
     */
    public User getUser(long id) {
        User user = null;
        user = userDao.findOne(id);
        return user;
    }

    /**
     * 读取用户列表
     *
     * @return
     */
    public List<User> getUserList() {
        List<User> userList = (List<User>) this.userDao.findAll();
        return userList;
    }

    /**
     * 根据username值查找user
     *
     * @param username
     * @return
     */
    public User findUserByUsername(String username) {
        return this.userDao.findUserByUsername(username);
    }

    /**
     * 按页面传来的查询条件查询用户.
     */
    public List<User> searchUser(Map<String, Object> searchParams) {
        Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
        Specification<User> spec = DynamicSpecifications.bySearchFilter(filters.values(), User.class);
        List<User> userList = userDao.findAll(spec);

        // 业务日志演示
        if (businessLogger != null) {
            businessLogger.log("USER", "LIST", getCurrentUsername(), null);
        }
        return userList;
    }

    /**
     * 读取所有角色信息
     *
     * @return
     */
    public List<Role> getAllRole() {
        return (List<Role>) this.roleDao.findAll();
    }

    private void encryptPassword(User user) {
        byte[] salt = Digests.generateSalt(SALT_SIZE);
        user.setSalt(Encodes.encodeHex(salt));

        byte[] hashPassword = Digests.sha1(user.getPlainPassword().getBytes(), salt, HASH_INTERATIONS);
        user.setPassword(Encodes.encodeHex(hashPassword));
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public void setRoleDao(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    public void setBusinessLogger(BusinessLogger businessLogger) {
        this.businessLogger = businessLogger;
    }

    public void setNotifyMessageProducer(NotifyMessageProducer notifyMessageProducer) {
        this.notifyMessageProducer = notifyMessageProducer;
    }
}
