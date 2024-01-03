package com.hello.first.account;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AccountRepository repo;
    //@Autowired
    //private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = repo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("not found " + username));
        UserDetails details = User.builder()
                .username(account.getUsername())
                .roles(account.getRole())
                .password(account.getPassword())
                //.passwordEncoder(p -> passwordEncoder.encode(p))
                .build();
        return details;
    }
}