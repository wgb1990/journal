package com.oakhole.auth.web;

import com.google.common.collect.Maps;
import com.oakhole.auth.entity.User;
import com.oakhole.auth.service.UserService;
import com.oakhole.core.uitls.Servlets;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 * @since 14-3-7
 */
@SuppressWarnings("ALL")
@Controller
@RequestMapping("/user")
public class UserController {

    private static Map<String, String> allStatus = Maps.newHashMap();

    static {
        allStatus.put("enabled", "有效");
        allStatus.put("disabled", "无效");
    }

    @Autowired
    private UserService userService;

    @RequestMapping(value = "update/{id}", method = RequestMethod.GET)
    public String updateForm(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", this.userService.getUser(id));
        model.addAttribute("allStatus", allStatus);
        model.addAttribute("allRoles", this.userService.getAllRole());

        return "auth/user/update";
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    public String update(@Valid @ModelAttribute(value = "user") User user, RedirectAttributes redirectAttributes) {
        this.userService.updateUser(user);
        redirectAttributes.addFlashAttribute("message", "更新用户" + user.getUsername() + "成功");
        return "redirect:/user";
    }

    @RequiresRoles(value = {"Admin", "User"}, logical = Logical.OR)
    @RequestMapping(value = "")
    public String list(Model model, ServletRequest request) {
        Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");

        List<User> users = this.userService.searchUser(searchParams);
        model.addAttribute("users", users);
        model.addAttribute("allStatus", allStatus);
        return "auth/user/list";
    }

    @ModelAttribute
    public void getUser(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
        if (id != -1) {
            model.addAttribute("user", this.userService.getUser(id));
        }
    }

    /**
     * 不自动绑定对象中的roleList属性，另行处理。
     */
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setDisallowedFields("roleList");
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
