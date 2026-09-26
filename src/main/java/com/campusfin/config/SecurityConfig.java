package com.campusfin.config;

import com.campusfin.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;

    public SecurityConfig(
            CustomUserDetailsService customUserDetailsService) {

        this.customUserDetailsService =
                customUserDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(
            PasswordEncoder passwordEncoder) {

        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider(
                        customUserDetailsService
                );

        provider.setPasswordEncoder(passwordEncoder);

        return provider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            DaoAuthenticationProvider authenticationProvider)
            throws Exception {

        http

                .authenticationProvider(
                        authenticationProvider
                )

                .authorizeHttpRequests(auth -> auth

                        /*
                         * CampusFin can be used without an account.
                         *
                         * Signing up is optional.
                         */
                        .requestMatchers(
                                "/",
                                "/login",
                                "/signup",
                                "/privacy",
                                "/terms",
                                "/error",

                                "/college-cost",
                                "/monthly-budget",
                                "/financial-readiness",
                                "/college-comparison/**",
                                "/what-if",
                                "/ai-financial-analysis",

                                "/css/**",
                                "/js/**",
                                "/images/**"
                        )
                        .permitAll()

                        /*
                         * Any future route not listed above
                         * remains protected by default.
                         */
                        .anyRequest()
                        .authenticated()
                )

                .formLogin(form -> form

                        .loginPage("/login")

                        .usernameParameter("username")

                        .passwordParameter("password")

                        .defaultSuccessUrl(
                                "/",
                                true
                        )

                        .failureUrl(
                                "/login?error"
                        )

                        .permitAll()
                )

                .logout(logout -> logout

                        .logoutUrl("/logout")

                        .logoutSuccessUrl(
                                "/?logout"
                        )

                        .permitAll()
                );

        return http.build();
    }
}