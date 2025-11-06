package cn.skylark.permission.authentication;

import cn.skylark.permission.authentication.filter.JwtAuthenticationFilter;
import cn.skylark.permission.authentication.handler.CustomLogoutSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

/**
 * @author yaomianwei
 * @since 15:47 2025/11/1
 **/
@Configuration
@EnableWebSecurity
@SuppressWarnings("deprecation")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Resource
  private UserDetailsService customUserDetailsService;

  @Resource
  @Lazy
  private CustomLogoutSuccessHandler customLogoutSuccessHandler;

  @Resource
  private JwtAuthenticationFilter jwtAuthenticationFilter;

  @Override
  @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Bean
  public static NoOpPasswordEncoder passwordEncoder() {
    return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    // use user information in database for authentication
    auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable()
            .authorizeRequests()
            .antMatchers("/oauth/token").permitAll()
            .antMatchers("/oauth/authorize").authenticated()
            .antMatchers("/oauth/**").permitAll()
            .antMatchers("/api/**").authenticated()
            .anyRequest().authenticated()
            .and()
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .formLogin()
            .and()
            .logout()
            .logoutUrl("/oauth/logout")
            .logoutSuccessHandler(customLogoutSuccessHandler)
            .permitAll();
  }
}
