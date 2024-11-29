package com.academia.arcademia_backend.security;

import com.academia.arcademia_backend.entity.UserInfo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class CustomUserDetails implements org.springframework.security.core.userdetails.UserDetails {

    private final String username;
    private final String password;
    private final List<GrantedAuthority> authorities;

    public CustomUserDetails(UserInfo userInfo) {
        username = userInfo.getUsername();
        password = userInfo.getPassword();
        authorities = Arrays.stream(userInfo.getRoles().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }
}
