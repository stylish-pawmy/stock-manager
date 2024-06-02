package org.example.gestionstock.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.security.web.util.RedirectUrlBuilder;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URL;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {
    private final UserDetailsService userDetailsService;

    public SecurityConfiguration(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
    {
        http
                .authorizeHttpRequests(request -> {
                    request.requestMatchers("/resources/**").permitAll()
                            .requestMatchers("graphics/**").permitAll()
                            .requestMatchers("/webjars/bootstrap/**").permitAll()
                            .anyRequest().authenticated();
                });

        http.formLogin(form -> {
            form.loginPage("/login")
                    .usernameParameter("username")
                    .passwordParameter("password").permitAll();
        });

        http.csrf(option -> {
            option.disable();
        });

        var logoutHandler = new SimpleUrlLogoutSuccessHandler();

        http.logout(logout -> {
            var url = UriComponentsBuilder
                    .fromUriString("/login")
                    .build()
                    .toString();

            logout.logoutSuccessUrl(url);
        });

        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        var auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userDetailsService);
        auth.setPasswordEncoder(NoOpPasswordEncoder.getInstance());

        return auth;
    }
}
