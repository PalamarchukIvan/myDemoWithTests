package com.example.demowithtests.config;

import com.example.demowithtests.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
@Log4j2
public class WebSecurityConfig {

    private final DataSource dataSource;
    private final UserRepository repository;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .antMatchers("/").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                        .loginPage("/login")
                        .permitAll()
                )
                .logout((logout) ->
                        logout.permitAll());

        return http.build();
    }

    @Bean
    public UserDetailsService users() {
        JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
        if(repository.findUserByUsername("user") == null) {
            UserDetails user = User.builder()
                    .username("user")
                    .password(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode("123"))
                    .roles("USER")
                    .build();
            users.createUser(user);
        }
        if(repository.findUserByUsername("admin") == null) {
            UserDetails admin = User.builder()
                    .username("admin")
                    .password(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode("123"))
                    .roles("USER", "ADMIN")
                    .build();
            users.createUser(admin);
        }
        return users;
    }

}
