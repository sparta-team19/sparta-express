package com.sparta_express.auth.security.handler;

import com.sparta_express.auth.user.entity.User;
import com.sparta_express.auth.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepository.findByEmailAndIsDelted(email, Boolean.FALSE)
            .orElseThrow(
                () -> new UsernameNotFoundException("Not found" + email));

        return new UserDetailsImpl(user);
    }
}
