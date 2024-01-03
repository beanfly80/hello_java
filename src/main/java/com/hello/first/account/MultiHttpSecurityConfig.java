package com.hello.first.account;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class MultiHttpSecurityConfig {

    @Bean
    public UserDetailsService userDetailsService() throws Exception {
        // ensure the passwords are encoded properly
        UserBuilder users = User.withDefaultPasswordEncoder();
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(
                users.username("user1").password("pw1").roles("USER").build());
        manager.createUser(
                users.username("user2").password("pw2").roles("USER").build());
        manager.createUser(
                users.username("user3").password("pw3").roles("USER", "ADMIN").build());
        return manager;
    }

    @Bean
    @Order(1)
    public SecurityFilterChain apiFilterChain(HttpSecurity http)
            throws Exception {
        http
                .securityMatcher("/account/**")
                .authorizeHttpRequests(authorize -> authorize.anyRequest().hasRole("ADMIN"))
                .httpBasic(withDefaults());
        return http.build();
    }

    @Bean
    public SecurityFilterChain formLoginFilterChain(HttpSecurity http)
            throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated())
                .formLogin(withDefaults());
        return http.build();
    }
}
