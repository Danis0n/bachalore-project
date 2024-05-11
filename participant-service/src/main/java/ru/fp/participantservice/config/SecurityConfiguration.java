package ru.fp.participantservice.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.fp.participantservice.service.ParticipantService;
import ru.fp.participantservice.service.auth.AuthorizationService;

import java.io.IOException;
import java.util.Optional;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final AuthorizationService authorizationService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterAt(this::authorizationFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(config -> config
                        .requestMatchers("/api/authentication/login",
                                "/api/authentication/register",
                                "/api/authentication/refresh",
                                "/",
                                "/built/bundle.js",
                                "/favicon.ico",
                                "/api/transfer"
                        )
                        .permitAll()
                )
                .authorizeHttpRequests(config -> config.anyRequest().authenticated())
                .sessionManagement(config -> config.sessionCreationPolicy(STATELESS))
                .exceptionHandling(conf -> conf.authenticationEntryPoint(this::authenticationFailedHandler))
                .build();
    }

    private void authorizationFilter(ServletRequest request, ServletResponse response, FilterChain chain
    ) throws IOException, ServletException {
        Optional<Authentication> authorization = this.authorize((HttpServletRequest) request);

        authorization.ifPresent(SecurityContextHolder.getContext()::setAuthentication);
        chain.doFilter(request, response);
    }

    private Optional<Authentication> authorize(HttpServletRequest request) {
        return authorizationService.authorize(request);
    }

    private void authenticationFailedHandler(HttpServletRequest request,
                                             HttpServletResponse response,
                                             AuthenticationException authException) {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(ParticipantService participantService) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(participantService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

}
