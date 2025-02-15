package com.github.son_daehyeon.domain.instance.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.github.son_daehyeon.domain.instance.schema.Instance;
import com.github.son_daehyeon.domain.user.schema.User;

@Repository
public interface InstanceRepository extends MongoRepository<Instance, String> {

    Optional<Instance> findTopByOrderByVmidDesc();

    List<Instance> findAllByUser(User user);

    boolean existsByIp(String ip);
}
