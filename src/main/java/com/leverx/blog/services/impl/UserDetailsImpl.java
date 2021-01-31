package com.leverx.blog.services.impl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.leverx.blog.entities.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

/** @author Andrey Egorov */
@Getter
public class UserDetailsImpl implements UserDetails {

  private static final long serialVersionUID = 1L;

  private final Long id;
  private final String firstName;
  private final String lastName;
  private final String email;
  @JsonIgnore private final String password;

  private Collection<? extends GrantedAuthority> authorities;

  public UserDetailsImpl(
      Long id,
      String firstName,
      String lastName,
      String email,
      String password,
      Collection<? extends GrantedAuthority> authorities) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.password = password;
    this.authorities = authorities;
  }

  public static UserDetailsImpl build(User user) {

    List<GrantedAuthority> authorities =
        Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));

    return new UserDetailsImpl(
        user.getId(),
        user.getFirstName(),
        user.getLastName(),
        user.getEmail(),
        user.getPassword(),
        authorities);
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    UserDetailsImpl user = (UserDetailsImpl) o;
    return Objects.equals(id, user.id);
  }
}
