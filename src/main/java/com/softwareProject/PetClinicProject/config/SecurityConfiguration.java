package com.softwareProject.PetClinicProject.config;

import com.softwareProject.PetClinicProject.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .authorizeHttpRequests()
                .requestMatchers("/auth/**")
                .permitAll()
                .requestMatchers("/doctor")
                .permitAll()
                .requestMatchers("/user/forgotPassword")
                .permitAll()
                .requestMatchers("/doctor/findByFirstName")
                .permitAll()
                .requestMatchers("/doctor/findByLastName")
                .permitAll()
                .requestMatchers("/doctor/findByFullName")
                .permitAll()
                .requestMatchers("/facility")
                .permitAll()
                .requestMatchers("/facility/findByNameContaining")
                .permitAll()
                .requestMatchers("/facility/findByPrice")
                .permitAll()
                .requestMatchers("/ws/**")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .cors();


        return http.build();
    }
}
