package com.github.son_daehyeon.domain.user.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.github.son_daehyeon.domain.user.schema.User;

public interface UserRepository extends MongoRepository<User, String> {
}
