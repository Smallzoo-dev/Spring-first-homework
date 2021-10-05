package com.sparta.springfirsthomework.security;

import com.sparta.springfirsthomework.domain.model.UserNormal;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class UserDetailsImpl implements UserDetails{
    public final UserNormal userNormal;

    public UserDetailsImpl(UserNormal userNormal) {
        this.userNormal = userNormal;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return userNormal.getPassword();
    }

    @Override
    public String getUsername() {
        return userNormal.getUsername();
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
}
