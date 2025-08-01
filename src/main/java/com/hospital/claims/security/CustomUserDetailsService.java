package com.hospital.claims.security;

import com.hospital.claims.entity.User;
import com.hospital.claims.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        // Ensure role is non-null and has the ROLE_ prefix
        String role = user.getRole() == null || user.getRole().isBlank()
                ? "USER"
                : user.getRole().toUpperCase();
        String authority = role.startsWith("ROLE_") ? role : "ROLE_" + role;

        List<GrantedAuthority> authorities =
                Collections.singletonList(new SimpleGrantedAuthority(authority));

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(authorities)
                .build();
    }
}
