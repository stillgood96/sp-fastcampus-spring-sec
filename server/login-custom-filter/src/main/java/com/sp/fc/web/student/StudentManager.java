package com.sp.fc.web.student;

import java.util.HashMap;
import java.util.Set;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;


@Component
public class StudentManager implements AuthenticationProvider, InitializingBean {
  private HashMap<String,Student> studentDb = new HashMap<>();

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {

    StudentAuthenticationToken token =
        (StudentAuthenticationToken)  authentication;

    if(studentDb.containsKey(token.getCredentials())) {
      Student student = studentDb.get(token.getCredentials());

      return StudentAuthenticationToken.builder()
          .principal(student)
          .details(student.getUsername())
          .authenticated(true)
          .build();

    }

    // 내가 처리할 수 없는 토큰은 핸들링을 해서는 안된다 그래서 null 로 내려준다.
    return null;
  }

  @Override
  public boolean supports(Class<?> authentication) {

    // UsernamePasswordAuthenticationToken.class 유형의 토큰을 받는다면 AuthenticationProvider의 역할을 하겠다.
    return authentication == StudentAuthenticationToken.class;
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    Set.of(
        new Student("a", "에이", Set.of(new SimpleGrantedAuthority("ROLE_STUDENT"))),
        new Student("b", "비", Set.of(new SimpleGrantedAuthority("ROLE_STUDENT"))),
        new Student("c", "씨", Set.of(new SimpleGrantedAuthority("ROLE_STUDENT")))
    ).forEach(e -> {
      studentDb.put(e.getId(), e);
    });
  }
}
