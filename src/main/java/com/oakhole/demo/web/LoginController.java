package com.oakhole.demo.web;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.eclipse.jetty.security.authentication.FormAuthenticator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Administrator
 * @since 14-2-17
 */
@Controller
public class LoginController {

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String fail(@RequestParam(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM) String username,Model model) {
        model.addAttribute(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM);
        return "login";
    }
}
