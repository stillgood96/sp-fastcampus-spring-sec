package com.sp.fc.web.config;


import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


/**
 * boot 2.7 이상이면 security는 5.7 버전을 사용하게되는데 이때
 * WebSecurityConfigurerAdapter class를 extends 받아서 사용하는데 이게 deprecated 되었다고한다.
 * bean으로 등록해서 사용하라고 하는데 이는 공식문서를 참조해서 진행해야할 것 같은데 흠 .. 일단
 * 최신버전에 맞춰서 작성할 수 있도록 해보겠다.
 */
@EnableWebSecurity(debug = true)
@EnableGlobalMethodSecurity(prePostEnabled = true)  // 메서드가 실행되기전에 권한체크를 하겠다라 어노테이션
public class SecurityConfig  {

  /**
   * configurer 설정
   * extends WebSecurityConfigurerAdapter version
   *@Configuration
   * public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
   *
   *     @Override
   *     protected void configure(HttpSecurity http) throws Exception {
   *         http
   *             .authorizeHttpRequests((authz) -> authz
   *                 .anyRequest().authenticated()
   *             )
   *             .httpBasic(withDefaults());
   *     }
   *
   * }
   *
   */
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

    http
        .authorizeHttpRequests((requests) ->
            requests
                .antMatchers("/").permitAll()
                .anyRequest()
                .authenticated()
        )
        .httpBasic(withDefaults());

    return http.build();
  }


  /**
   * InMemory에 유저추가. 옛날버전
   * extends WebSecurityConfigurerAdapter version
   *   @Configuration
   *   public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
   *     @Override
   *     protected void configure(AuthenticationManagerBuilder auth) throws Exception {
   *       UserDetails user = User.withDefaultPasswordEncoder()
   *           .username("user")
   *           .password("password")
   *           .roles("USER")
   *           .build();
   *       auth.inMemoryAuthentication()
   *           .withUser(user);
   *     }
   *   }
   *
   *   InMemory에 유저추가. 5.7ver
   *   소스코드로 관리자를 추가하게되면 application.yml에 설정하 유저아이디는 로그인할 수 없게 됨
   */
  @Bean
  public InMemoryUserDetailsManager userDetailsService() {
    UserDetails user2 = User.builder()
        .username("user2")
        .password(passwordEncoder().encode("2222"))
        .roles("USER")
        .build();

    UserDetails user3 = User.builder()
        .username("user3")
        .password(passwordEncoder().encode("3333"))
        .roles("ADMIN")
        .build();

    return new InMemoryUserDetailsManager(user2, user3);
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }


}
