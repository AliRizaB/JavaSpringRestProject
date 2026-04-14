package com.example.dims_project_2.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.security.autoconfigure.web.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
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
@EnableMethodSecurity
public class SecurityFilterConfig {
    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable());

        http


                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/login", "/access_denied",
                                "/bootstrap/**", "/css/**", "/js/**", "/images/**",
                                "/order/read", "/product/read", "/customer/read").permitAll()
                        .requestMatchers("/order/read", "/order/info/**", "/product/read", "/product/info/**", "/customer/read", "/customer/info/**", "/user/read", "/user/info/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers(
                                "/customer/delete", "/customer/delete/**", "/customer/create", "/customer/update/**", "/customer/update",
                                "/order/delete", "/order/delete/**", "/order/create", "/order/update/**", "/order/update"
                                , "/product/delete", "/product/delete/**", "/product/create", "/product/update/**", "/product/update",
                                "/user/delete", "/user/delete/**", "/user/create", "/user/update/**", "/user/update").hasRole("ADMIN")

                        .anyRequest().authenticated())

                .formLogin(login -> login
                        .loginPage("/login")
                        .successHandler(new AuthenticationSuccessHandler() {
                            @Override
                            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                                                Authentication authentication) throws IOException, ServletException {
                                Collection<SimpleGrantedAuthority> authorities = (Collection<SimpleGrantedAuthority>) SecurityContextHolder.getContext().getAuthentication().getAuthorities();

                                String role = authorities.iterator().next().getAuthority().substring(5);
                                switch (role) {
                                    case "USER":
                                        redirectStrategy.sendRedirect(request, response, "/");
                                        break;
                                    case "ADMIN":
                                        redirectStrategy.sendRedirect(request, response, "/user/read");
                                        break;
                                    case "ANONYMOUS":
                                        redirectStrategy.sendRedirect(request, response, "/");
                                    default:
                                        redirectStrategy.sendRedirect(request, response, "/");
                                }
                            }
                        }))

                .exceptionHandling(exception -> exception

                        .authenticationEntryPoint((request, response, authException) -> {
                            response.sendRedirect("/access_denied");
                        })

                        .accessDeniedPage("/access_denied")
                )

                .logout(logout -> logout
                        .logoutUrl("/logout").logoutSuccessUrl("/").clearAuthentication(true)

                        .deleteCookies("JSESSIONID")
                        .invalidateHttpSession(true));

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
