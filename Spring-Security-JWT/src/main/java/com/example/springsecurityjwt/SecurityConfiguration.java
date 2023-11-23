package com.example.springsecurityjwt;

import com.example.springsecurityjwt.Filters.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {


    @Bean
    public SecurityFilterChain filter(HttpSecurity chain) throws Exception {

        UserDetailsServiceImpl userDetailsService = new UserDetailsServiceImpl(encoder());

        JwtRequestFilter jwtRequestFilter = new JwtRequestFilter(userDetailsService);

        chain.csrf(AbstractHttpConfigurer::disable);
        chain.authorizeHttpRequests(request ->
        request.requestMatchers("/authenticate").permitAll().anyRequest().authenticated());
        chain.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        chain.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return chain.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

}
