package com.sp.fc.web.teacher;

import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Teacher {

  private String id;
  private String username;
  private Set<GrantedAuthority> role;

}
