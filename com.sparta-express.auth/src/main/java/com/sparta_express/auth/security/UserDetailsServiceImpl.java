package com.sparta_express.auth.security;

import com.sparta_express.auth.common.CustomException;
import com.sparta_express.auth.common.ErrorType;
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

        User user = userRepository.findByEmailAndIsDeleted(email, Boolean.FALSE)
            .orElseThrow(
                () -> new CustomException(ErrorType.NOT_FOUND_USER));

        return new UserDetailsImpl(user);
    }
}
