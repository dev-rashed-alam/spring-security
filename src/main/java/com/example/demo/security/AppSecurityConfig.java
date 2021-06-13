package com.example.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import static com.example.demo.security.AppUserRole.*;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AppSecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/**").hasRole(STUDENT.name())
                .anyRequest().authenticated()
                .and().formLogin()
                .loginPage("/login").permitAll()
                .defaultSuccessUrl("/dashboard", true)
                .and().rememberMe().rememberMeParameter("remember");
    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        UserDetails studentUser = User.builder().username("Student").password(passwordEncoder.encode("password")).authorities(STUDENT.getGrantedAuthorities()).build();

        UserDetails adminUser = User.builder().username("Admin").password(passwordEncoder.encode("password")).authorities(ADMIN.getGrantedAuthorities()).build();

        UserDetails traineeUser = User.builder().username("Trainee").password(passwordEncoder.encode("password")).authorities(ADMINTRAINEE.getGrantedAuthorities()).build();

        return new InMemoryUserDetailsManager(studentUser, adminUser, traineeUser);
    }
}
