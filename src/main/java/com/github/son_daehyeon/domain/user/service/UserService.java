package com.github.son_daehyeon.domain.user.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.github.son_daehyeon.domain.user.dto.response.UsersResponse;
import com.github.son_daehyeon.domain.user.repository.UserRepository;
import com.github.son_daehyeon.domain.user.schema.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UsersResponse all() {

        List<User> users = userRepository.findAll();

        return UsersResponse.builder()
            .users(users)
            .build();
    }
}
