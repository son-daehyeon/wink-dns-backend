package com.github.son_daehyeon.domain.project.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.github.son_daehyeon.domain.project.schema.Project;
import com.github.son_daehyeon.domain.user.schema.User;

@Repository
public interface ProjectRepository extends MongoRepository<Project, String> {

    List<Project> findAllByParticipantsContains(User user);
    List<Project> findAllByPendingContains(User user);
}
