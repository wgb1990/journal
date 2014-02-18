package com.oakhole.demo.web;

import com.oakhole.demo.entity.AppUser;
import com.oakhole.demo.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

/**
 * @author Administrator
 * @since 14-2-17
 */
@Controller
@RequestMapping(value="/user")
public class UserController {

    @Autowired
    private AppUserService appUserService;

    @RequestMapping(method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute(this.appUserService.getAllUsers());
        return "user/userList";
    }

    @RequestMapping(value = "add",method = RequestMethod.GET)
    public String addUser() {
        return "user/add";
    }

    @RequestMapping(value="add",method=RequestMethod.POST)
    public String addUser(@Valid @ModelAttribute AppUser appUser,RedirectAttributes redirectAttributes) {
        this.appUserService.saveUser(appUser);
        redirectAttributes.addFlashAttribute("message", "Create user success");
        return "redirect:/user/";
    }

    /**
     * generate getter and setter
     * @param appUserService
     */
    public void setAppUserService(AppUserService appUserService) {
        this.appUserService = appUserService;
    }
}
