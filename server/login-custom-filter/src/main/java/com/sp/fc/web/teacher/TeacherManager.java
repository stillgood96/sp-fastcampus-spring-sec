package com.sp.fc.web.teacher;

import com.sp.fc.web.student.Student;
import com.sp.fc.web.student.StudentAuthenticationToken;
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
public class TeacherManager implements AuthenticationProvider, InitializingBean {
  private HashMap<String, Teacher> teacherDb = new HashMap<>();

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {

    TeacherAuthenticationToken token =
        (TeacherAuthenticationToken)  authentication;


    if(teacherDb.containsKey(token.getCredentials())) {
      Teacher teacher = teacherDb.get(token.getCredentials());

      return TeacherAuthenticationToken.builder()
          .principal(teacher)
          .details(teacher.getUsername())
          .authenticated(true)
          .build();

    }

    // 내가 처리할 수 없는 토큰은 핸들링을 해서는 안된다 그래서 null 로 내려준다.
    return null;
  }

  @Override
  public boolean supports(Class<?> authentication) {

    // UsernamePasswordAuthenticationToken.class 유형의 토큰을 받는다면 AuthenticationProvider의 역할을 하겠다.
    return authentication == TeacherAuthenticationToken.class;
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    Set.of(
        new Teacher("lee", "에이", Set.of(new SimpleGrantedAuthority("ROLE_TEACHER")))
    ).forEach(e -> {
      teacherDb.put(e.getId(), e);
    });
  }
}
