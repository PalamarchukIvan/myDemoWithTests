package com.example.demowithtests.util.config;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // TODO: 18-Oct-22 Create 2 users for demo
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.inMemoryAuthentication()

                .withUser("user").password("{noop}password").roles("USER")
                .and()
                .withUser("admin").password("{noop}password").roles("USER", "ADMIN");

    }

    // TODO: 18-Oct-22 Secure the endpoins with HTTP Basic authentication
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                //HTTP Basic authentication
                .httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/employees/**").hasRole("USER")
                .antMatchers(HttpMethod.POST, "/api/employees").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/employees/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.PATCH, "/api/employees/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/employees/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/api/badges/**").hasRole("USER")
                .and()
                .csrf().disable()
                .formLogin().disable();
    }
}
