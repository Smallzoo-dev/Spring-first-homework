package com.sparta.springfirsthomework.security;

import com.sparta.springfirsthomework.domain.model.UserNormal;
import com.sparta.springfirsthomework.repository.UserNormalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserNormalDetailsServiceImpl implements UserDetailsService {
    private final UserNormalRepository userNormalRepository;

    // @RequiredArgsConstructor 로 세터메서드로 주입받으면 안됨???
    @Autowired
    public UserNormalDetailsServiceImpl(UserNormalRepository userNormalRepository) {
        this.userNormalRepository = userNormalRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserNormal userNormal = userNormalRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Can't find " + username));
        return new UserDetailsImpl(userNormal);
    }
}
