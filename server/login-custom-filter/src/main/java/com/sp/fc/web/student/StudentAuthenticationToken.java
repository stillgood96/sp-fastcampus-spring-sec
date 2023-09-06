package com.sp.fc.web.student;

import java.util.Collection;
import java.util.HashSet;
import org.springframework.security.core.Authentication;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentAuthenticationToken implements Authentication {
  private Student principal;
  private String credentials;
  private String details;
  private boolean authenticated;


  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return principal == null ? new HashSet<>() : principal.getRole();
  }

  @Override
  public String getName() {
    return principal == null ? "" : principal.getUsername();
  }

}
