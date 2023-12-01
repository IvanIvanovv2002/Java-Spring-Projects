package com.example.springauthoauth2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.security.web.SecurityFilterChain;

import java.util.*;

@Configuration
public class Config {

    @Bean
    public SecurityFilterChain filter(HttpSecurity chain) throws Exception {

        chain.csrf(AbstractHttpConfigurer::disable);
        chain.authorizeHttpRequests(filter -> filter.requestMatchers("/", "/login").permitAll()
        .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
        .permitAll().anyRequest().hasAnyAuthority("USER"))
        .oauth2Login(login -> login.loginPage("/login").
        userInfoEndpoint(user -> user.userAuthoritiesMapper(new GrantedAuthoritiesMapperImpl())).defaultSuccessUrl("/", true))
        .formLogin(login -> login.loginPage("/login").defaultSuccessUrl("/secured", true));

        chain.logout(logout -> logout.invalidateHttpSession(true).clearAuthentication(true).deleteCookies("JSESSIONID"));
        return chain.build();
    }

    @Bean
    public UserDetails user() {
        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority("USER"));
        return new User("user", encoder().encode("12345"), authorityList);
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }


    private static class GrantedAuthoritiesMapperImpl implements GrantedAuthoritiesMapper {

        @Override
        public Collection<? extends GrantedAuthority> mapAuthorities(Collection<? extends GrantedAuthority> authorities) {
            Set<GrantedAuthority> mappedAuthorities = new HashSet<>();
            authorities.forEach(authority -> {
                 if (authority instanceof OAuth2UserAuthority) {
                    mappedAuthorities.add(new SimpleGrantedAuthority("USER"));
                }
            });

            return mappedAuthorities;
        }
        }
    }


