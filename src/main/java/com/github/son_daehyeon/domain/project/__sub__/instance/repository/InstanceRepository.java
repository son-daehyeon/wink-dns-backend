package com.github.son_daehyeon.domain.project.__sub__.instance.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.github.son_daehyeon.domain.project.schema.Project;
import com.github.son_daehyeon.domain.project.__sub__.instance.schema.Instance;

@Repository
public interface InstanceRepository extends MongoRepository<Instance, String> {

    List<Instance> findAllByProject(Project project);

    boolean existsByIp(String ip);
}
