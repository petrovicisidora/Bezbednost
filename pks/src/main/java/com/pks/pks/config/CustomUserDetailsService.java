package com.pks.pks.config;


import com.pks.pks.model.User;
import com.pks.pks.repository.UserRepository;
import com.pks.pks.security.Authority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> user = userRepository.findOneByEmail(username);
        org.springframework.security.core.userdetails.User.UserBuilder builder = null;

        if (!user.isPresent()) {
            throw new UsernameNotFoundException("USERNAME_NOT_FOUND");
        }

        builder = org.springframework.security.core.userdetails.User.withUsername(username);
        String password = user.get().getPassword();

        builder.password(password);
        builder.authorities(user.get().getRole().toString());


        return builder.build();
    }

    public Collection<? extends GrantedAuthority> getAuthoritiesFromUserDetails(UserDetails principle) {

        List<Authority> authorities = new ArrayList<>();

        Optional<User> user = userRepository.findOneByEmail(principle.getUsername());

        if(!user.isPresent()) {
            return authorities;
        }

        Authority authority = new Authority();
        authority.setName(user.get().getRole().toString());

        authorities.add(authority);

        return authorities;
    }

    public User findUserByEmail(String email) {
        return userRepository.findOneByEmail(email).get();
    }

}