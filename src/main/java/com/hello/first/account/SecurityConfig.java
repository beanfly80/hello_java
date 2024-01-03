package com.hello.first.account;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
  @Autowired
  private UserDetailsServiceImpl service;

  @Bean
  SecurityFilterChain filterChain(HttpSecurity http)
    throws Exception {
      http
      .authorizeHttpRequests(requests ->
        requests
          .requestMatchers("/chat")
          .authenticated()
          .requestMatchers("/**")
          .permitAll()
      )
      .formLogin(Customizer.withDefaults())
      .httpBasic(Customizer.withDefaults())
      .csrf((csrf) -> csrf.disable());

    //   .logout(Customizer.withDefaults());
    return http.build();
  }

  @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

  @Bean
  public UserDetailsService userDetailsService(DataSource dataSource) {
    return new JdbcUserDetailsManager(dataSource);
  }

  @Bean
  public DaoAuthenticationProvider daoAuthenticationProvider() throws Exception {
    DaoAuthenticationProvider p = new DaoAuthenticationProvider();
    p.setUserDetailsService(service);
    p.setPasswordEncoder(passwordEncoder());
    return p;
  }

}
