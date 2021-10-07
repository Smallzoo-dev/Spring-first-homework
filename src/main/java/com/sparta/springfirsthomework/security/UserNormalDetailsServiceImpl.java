package com.sparta.springfirsthomework.security;

import com.sparta.springfirsthomework.domain.model.UserNormal;
import com.sparta.springfirsthomework.repository.UserNormalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserNormalDetailsServiceImpl implements UserDetailsService {
    private final UserNormalRepository userNormalRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserNormal userNormal = userNormalRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Can't find " + username));
        return new UserDetailsImpl(userNormal);
    }
}
