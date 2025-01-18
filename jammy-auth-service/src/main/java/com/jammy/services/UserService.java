package com.jammy.services;

import com.jammy.dto.JammyUserDetails;
import com.jammy.dto.RegisterUserRequest;
import com.jammy.dto.UserModel;
import com.jammy.entities.UserEntity;
import com.jammy.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByUsername(username);
        if (userEntity == null) throw new UsernameNotFoundException(username);
        return JammyUserDetails.builder()
                .username(userEntity.getUsername())
                .password(userEntity.getPassword())
                .build();
    }

    public UserModel register(RegisterUserRequest request) {
        String hashedPassword = passwordEncoder.encode(request.getPassword());
        UserEntity entity = userRepository.save(
                UserEntity.builder()
                        .id(UUID.randomUUID())
                        .username(request.getUsername())
                        .email(request.getEmail())
                        .password(hashedPassword)
                        .build()
        );
        return new UserModel(
                entity.getId(),
                entity.getUsername(),
                entity.getEmail()
        );
    }
}
