package com.hello.first.account;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AccountService {

  private final AccountRepository repo;
  private final PasswordEncoder passwordEncoder;

  public Account create(String username, String password, String role) {
    if (username == null || password == null)
        throw new ExceptionInInitializerError();

    Account user = new Account();
    user.setUsername(username);
    user.setRole((role != null) ? "USER" : role);
    user.setPassword(passwordEncoder.encode(password));
    repo.save(user);
    return user;
  }

  public Account create(String username, String password) {
    return create(username, password, "USER");
  }

}
