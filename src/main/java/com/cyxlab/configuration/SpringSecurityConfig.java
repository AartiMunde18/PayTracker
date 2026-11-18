package com.cyxlab.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	    return http
	        .authorizeHttpRequests(auth -> auth
	            // 1. Autoriser l'accès libre à la console H2
	            .requestMatchers("/h2-console/**").permitAll()
	            .requestMatchers("/admin").hasRole("ADMIN")
	            .requestMatchers("/user").hasRole("USER")
	            .anyRequest().authenticated()
	        )
	        // 2. Désactiver le CSRF pour les URLs H2 et autoriser les iframes (nécessaire pour l'interface H2)
	        .csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**"))
	        .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()))
	        .formLogin(Customizer.withDefaults())
	        .build();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
	    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}	
	
	@Bean
	public AuthenticationManager authenticationManager(HttpSecurity http, PasswordEncoder passwordEncoder) throws Exception {
	    AuthenticationManagerBuilder authenticationManagerBuilder = 
	        http.getSharedObject(AuthenticationManagerBuilder.class);
	        
	    authenticationManagerBuilder
	        .userDetailsService(customUserDetailsService)
	        .passwordEncoder(passwordEncoder);
	        
	    return authenticationManagerBuilder.build();
	}
}