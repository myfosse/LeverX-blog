package com.leverx.blog.configs.security;

import com.leverx.blog.jwt.AuthEntryPointJwt;
import com.leverx.blog.jwt.AuthTokenFilter;
import com.leverx.blog.services.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/** @author Andrey Egorov */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired private UserDetailsServiceImpl userDetailsService;

  @Autowired private AuthEntryPointJwt unauthorizedHandler;

  private static final String PATH = "/api/v1/";

  @Bean
  public AuthTokenFilter authenticationJwtTokenFilter() {
    return new AuthTokenFilter();
  }

  @Override
  public void configure(final AuthenticationManagerBuilder authenticationManagerBuilder)
      throws Exception {
    authenticationManagerBuilder
        .userDetailsService(userDetailsService)
        .passwordEncoder(passwordEncoder());
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Override
  protected void configure(final HttpSecurity http) throws Exception {

    http.cors()
        .and()
        .csrf()
        .disable()
        .exceptionHandling()
        .authenticationEntryPoint(unauthorizedHandler)
        .and()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .authorizeRequests()
        .antMatchers("/api/v1/auth/**")
        .anonymous()
        .antMatchers(
            HttpMethod.GET,
            PATH + "/articles",
            PATH + "/{id:[\\d+]}/comments/**",
            PATH + "/tags-cloud",
            PATH + "/users/**")
        .permitAll()
        .antMatchers(HttpMethod.GET, PATH + "/articles/my")
        .hasAuthority("ROLE_USER")
        .antMatchers(HttpMethod.POST, PATH + "/articles", PATH + "/articles/{id:[\\d+]}/comments")
        .hasAuthority("ROLE_USER")
        .antMatchers(HttpMethod.PUT, PATH + "/articles/{id:[\\d+]}", PATH + "/users/{id:[\\d+]}")
        .hasAuthority("ROLE_USER")
        .antMatchers(
            HttpMethod.DELETE,
            PATH + "/articles/{id:[\\d+]}",
            PATH + "/articles/{id:[\\d+]}/comments/{commentID:[\\d+]",
            PATH + "/users/{id:[\\d+]}")
        .hasAuthority("ROLE_USER");

    http.addFilterBefore(
        authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
  }
}
