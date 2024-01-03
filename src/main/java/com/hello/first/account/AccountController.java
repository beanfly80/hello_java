package com.hello.first.account;

import com.hello.first.account.*;

import jakarta.validation.Valid;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/account")
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/signup")
    public String signup(AccountCreateForm accountCreateForm) {
        return "signup_form";
    }

    @PostMapping("/signup")
    public String signup(@Valid AccountCreateForm accountCreateForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "signup_form";
        }

        if (!accountCreateForm.getPassword1().equals(accountCreateForm.getPassword2())) {
            bindingResult.rejectValue("password2", "passwordInCorrect", 
                    "[password] & [confirm password] does not match.");
            return "signup_form";
        }

        try {
            accountService.create(accountCreateForm.getUsername(), accountCreateForm.getPassword1());
        }catch(DataIntegrityViolationException e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", "username already exists!");
            return "signup_form";
        }catch(Exception e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", e.getMessage());
            return "signup_form";
        }

        return "redirect:/chat";
    }
}
