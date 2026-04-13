package com.example.dims_project_2.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.util.Collection;

@Configuration
@EnableWebSecurity
public class SecurityFilterConfig {
    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable());

        http


                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/login", "/user/create", "/images/**", "/bootstrap/**", "/css/**", "/js/**").permitAll()
                        .requestMatchers("/user/all").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/user/add", "/user/update/**", "/user/update").hasRole("ADMIN")
                        .anyRequest().authenticated())

                .formLogin(login -> login
                        .loginPage("/login")
                        .successHandler(new AuthenticationSuccessHandler() {
                            @Override
                            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                                                Authentication authentication) throws IOException, ServletException {
                                Collection<SimpleGrantedAuthority> authorities = (Collection<SimpleGrantedAuthority>) SecurityContextHolder.getContext().getAuthentication().getAuthorities();

                                String role = authorities.iterator().next().getAuthority().substring(5);
                                switch(role){
                                    case "USER":
                                        redirectStrategy.sendRedirect(request, response, "/");
                                        break;
                                    case "ADMIN":
                                        redirectStrategy.sendRedirect(request, response, "/user/create");
                                        break;
                                    case "ANONYMOUS":
                                        redirectStrategy.sendRedirect(request, response, "/");
                                    default:
                                        redirectStrategy.sendRedirect(request, response, "/");
                                }
                            }
                        }))

                .exceptionHandling(exception -> exception
                        .accessDeniedPage("/access-denied"))

                .logout(logout -> logout
                        .logoutUrl("/logout").logoutSuccessUrl("/").clearAuthentication(true)
                        //.logoutUrl("/logout") //.clearAuthentication(true)
                        .deleteCookies("JSESSIONID")
                        .invalidateHttpSession(true));

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
