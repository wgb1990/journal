package com.oakhole.auth.service;

import com.oakhole.auth.entity.User;
import com.oakhole.auth.repository.UserDao;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Administrator
 * @since 14-3-18
 */
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserDao userDao;

    @Mock
    private BusinessLogger businessLogger;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        ShiroTestUtils.mockSubject(new ShiroDbRealm.ShiroUser("foo", "foo"));
    }

    @After
    public void tearDown() throws Exception {
        ShiroTestUtils.clearSubject();
    }

    @Test
    public void testSaveUser() throws Exception {
        User one = new User();
        one.setId(1L);
        one.setUsername("one");
        one.setPlainPassword("one");

        User two = new User();
        two.setId(2L);
        two.setUsername("two");
        two.setPlainPassword("two");

        this.userService.saveUser(one);
        this.userService.saveUser(two);

        Map mapData = new HashMap();
        mapData.put("userId", 1);
        this.businessLogger.log("User","UPDATE","one",mapData);

        Mockito.verify(userDao,Mockito.never()).delete(1L);
    }
}
