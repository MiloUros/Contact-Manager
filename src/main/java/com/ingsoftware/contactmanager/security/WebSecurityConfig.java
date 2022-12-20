package com.ingsoftware.contactmanager.security;

import com.ingsoftware.contactmanager.domain.enums.Role;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;


@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class WebSecurityConfig {

    @Autowired
    UserRepoDetailService userRepoDetailService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests(auth -> auth
                        .antMatchers(HttpMethod.POST, "/contact-types").hasAnyAuthority(Role.ADMIN.name())
                        .antMatchers(HttpMethod.PUT, "/contacts-types/{contactTypeUUID}").hasAnyAuthority(Role.ADMIN.name())
                        .antMatchers(HttpMethod.DELETE, "/contacts-types/{contactTypeUUID}").hasAnyAuthority(Role.ADMIN.name())
                        .antMatchers(HttpMethod.DELETE, "/contacts/{contactUUID}").hasAnyAuthority(Role.ADMIN.name(), Role.USER.name())
                        .antMatchers(HttpMethod.POST, "/users").hasAnyAuthority(Role.ADMIN.name())
                        .antMatchers(HttpMethod.PUT, "/users/{userUUID}").hasAnyAuthority(Role.ADMIN.name())
                        .antMatchers(HttpMethod.DELETE, "/users/{userUUID}").hasAnyAuthority(Role.ADMIN.name())
                        .anyRequest().authenticated()
                )
                .userDetailsService(userRepoDetailService)
                .httpBasic(withDefaults());
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder();}

}
