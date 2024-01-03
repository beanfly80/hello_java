package com.hello.first.account;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountCreateForm {
    @Size(min = 3, max = 19)
    @NotEmpty(message = "[username] is required.")
    private String username;

    @Size(min = 2, max = 19)
    @NotEmpty(message = "[password] is required")
    private String password1;

    @NotEmpty(message = "[confirm password] is required")
    private String password2;
}