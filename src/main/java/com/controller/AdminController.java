package com.controller;

import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminController {
    @Autowired
    private UserService userService;

    @GetMapping("/admin")
    public String userList(Model model) {
        model.addAttribute("allUsers", userService.allUsers());
        return "admin";
    }

    @PostMapping("/admin/blockUserAccount")
    public String  blockUserAccount(@RequestParam(defaultValue = "" ) String userName) {
        userService.blockUser(userName);
        return "redirect:/admin";
    }

    @PostMapping("/admin/unblockUserAccount")
    public String  unblockUserAccount(@RequestParam(defaultValue = "" ) String userName) {
        userService.unblockUser(userName);
        return "redirect:/admin";
    }
}
