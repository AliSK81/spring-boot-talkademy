package com.alisk.chatroom.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable();

        http.authorizeHttpRequests(requests -> requests
                        .antMatchers("/").permitAll()
                        .anyRequest().authenticated())
                .formLogin(login -> login.permitAll())
                .logout(logout -> logout.permitAll());

        return http.build();
    }

    @Configuration
    public static class HttpSession implements HttpSessionListener {

        @Override
        public void sessionCreated(HttpSessionEvent event) {
            event.getSession().setMaxInactiveInterval(15 * 60);
        }
    }

}