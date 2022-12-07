package com.ingsoftware.contactmanager.security;

import com.ingsoftware.contactmanager.domain.enums.Role;
import com.ingsoftware.contactmanager.services.UserRepoDetailService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
                        .antMatchers("/api/users").hasAnyAuthority(Role.ADMIN.name())
                        .antMatchers("/api/contacts/all").hasAnyAuthority(Role.ADMIN.name())
                        .antMatchers("/api/contacts").hasAnyAuthority(Role.ADMIN.name(), Role.USER.name())
                        .antMatchers("/api/contacts/{contactId}").hasAnyAuthority(Role.ADMIN.name(), Role.USER.name())
                        .antMatchers("/api/contacts/{contactUUID}").hasAnyAuthority(Role.ADMIN.name(), Role.USER.name())
                        .anyRequest().authenticated()
                )
                .userDetailsService(userRepoDetailService)
                .httpBasic(withDefaults());
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder();}


}
