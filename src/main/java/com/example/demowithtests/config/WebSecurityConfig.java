package com.example.demowithtests.config;

import com.example.demowithtests.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
@Log4j2
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final DataSource dataSource;
    private final UserRepository repository;

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic()
                .and()
                .authorizeHttpRequests()
                .antMatchers("/").permitAll()
                .antMatchers(HttpMethod.GET, "/api/**").hasRole("USER")
                .antMatchers(HttpMethod.POST, "/api/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.PATCH, "/api/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin().disable()
                .csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(encoder())
                .usersByUsernameQuery(
                        "select username, password, enabled from users where username = ?")
                .authoritiesByUsernameQuery(
                        "select username, authority from authorities where username = ?");
    }

    @Bean
    public UserDetailsService users() {
        JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
        if (repository.findUserByUsername("user") == null) {
            UserDetails user = User.builder()
                    .username("user")
                    .password(encoder().encode("123"))
                    .roles("USER")
                    .build();
            users.createUser(user);
        }
        if (repository.findUserByUsername("admin") == null) {
            UserDetails admin = User.builder()
                    .username("admin")
                    .password(encoder().encode("123"))
                    .roles("USER", "ADMIN")
                    .build();
            users.createUser(admin);
        }
        return users;
    }
}
