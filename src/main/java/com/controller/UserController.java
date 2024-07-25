package com.controller;

import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/account")
    public String getUserAccount(Model model, Principal principal) {
        model.addAttribute("account", userService.getAccountByUserName(principal.getName()));
        return "account";
    }

    @PostMapping("/account/topUp")
    public String  topUpUserAccount(@RequestParam(defaultValue = "" ) String name,
                                    @RequestParam(defaultValue = "" ) Integer amount) {
        userService.topUpAccount(name, amount);
        return "redirect:/account";
    }

    @PostMapping("/account/withdraw")
    public String  withdrawFromUserAccount(@RequestParam(defaultValue = "" ) String name,
                                           @RequestParam(defaultValue = "" ) Integer amount) {
        userService.withdrawFromAccount(name, amount);
        return "redirect:/account";
    }
}
