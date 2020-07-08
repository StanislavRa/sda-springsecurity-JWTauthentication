package com.sda.authentificationdemo.config;

/**
 * @author StanislavR
 */

import com.sda.authentificationdemo.security.MyAuthenticationSuccessHandler;
import com.sda.authentificationdemo.security.jwt.JwtConfigurer;
import com.sda.authentificationdemo.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;
    private final MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;
    private final LogoutSuccessHandler customLogoutHandler;
    private static final String USER_ENDPOINT = "/users";
    private static final String ADMIN_ENDPOINT = "/users/admin/**";

    @Autowired
    public SecurityConfig(JwtTokenProvider jwtTokenProvider, MyAuthenticationSuccessHandler myAuthenticationSuccessHandler, LogoutSuccessHandler customLogoutHandler) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.myAuthenticationSuccessHandler = myAuthenticationSuccessHandler;
        this.customLogoutHandler = customLogoutHandler;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/", "/auth/**", "/js/**", "/css/**", "/webjars/**").permitAll()
                .antMatchers(ADMIN_ENDPOINT).hasRole("ADMIN")
                .antMatchers(USER_ENDPOINT).hasRole("USER")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/auth/login")
                .successHandler(myAuthenticationSuccessHandler)
//
                .and()
                .logout()
                .logoutSuccessHandler(customLogoutHandler)
                .logoutSuccessUrl("/auth/login")
             //   .deleteCookies("access_token")
                .and()
                .apply(new JwtConfigurer(jwtTokenProvider));
    }
}
