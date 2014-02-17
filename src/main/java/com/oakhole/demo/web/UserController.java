package com.oakhole.demo.web;

import com.oakhole.demo.entity.AppUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

/**
 * @author Administrator
 * @since 14-2-17
 */
@Controller
public class UserController {

    @RequestMapping(value = "/user/add",method = RequestMethod.GET)
    public String addUser() {
        return "/user/add";
    }

    @RequestMapping(value="/user/add",method=RequestMethod.POST)
    public String addUser(@Valid @ModelAttribute AppUser appUser) {
        // TODO : add user
        return "/user/list";
    }
}
