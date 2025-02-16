package com.github.son_daehyeon.domain.project.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.github.son_daehyeon.domain.project.__sub__.instance.repository.InstanceRepository;
import com.github.son_daehyeon.domain.project.__sub__.instance.schema.Instance;
import com.github.son_daehyeon.domain.project.dto.request.CreateProjectRequest;
import com.github.son_daehyeon.domain.project.dto.response.ProjectResponse;
import com.github.son_daehyeon.domain.project.dto.response.ProjectsResponse;
import com.github.son_daehyeon.domain.project.exception.AlreadyProjectInvitedException;
import com.github.son_daehyeon.domain.project.exception.AlreadyProjectParticipantException;
import com.github.son_daehyeon.domain.project.exception.ProjectHasInstanceException;
import com.github.son_daehyeon.domain.project.exception.ProjectNotFoundException;
import com.github.son_daehyeon.domain.project.repository.ProjectRepository;
import com.github.son_daehyeon.domain.project.schema.Project;
import com.github.son_daehyeon.domain.user.exception.UserNotFoundException;
import com.github.son_daehyeon.domain.user.repository.UserRepository;
import com.github.son_daehyeon.domain.user.schema.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final InstanceRepository instanceRepository;

    public ProjectsResponse myProjects(User user) {

        return ProjectsResponse.builder()
            .projects(projectRepository.findAllByParticipantsContains(user))
            .build();
    }

    public ProjectsResponse invitedProjects(User user) {

        return ProjectsResponse.builder()
            .projects(projectRepository.findAllByPendingContains(user))
            .build();
    }

    public ProjectResponse createProject(CreateProjectRequest dto, User user) {

        Project project = Project.builder()
            .name(dto.name())
            .participants(List.of(user))
            .build();

        return ProjectResponse.builder()
            .project(projectRepository.save(project))
            .build();
    }

    public ProjectResponse inviteUser(String projectId, String userId, User user) {

        Project project = projectRepository.findById(projectId)
            .filter(p -> p.getParticipants().contains(user))
            .orElseThrow(ProjectNotFoundException::new);

        User target = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        if (project.getParticipants().contains(target)) throw new AlreadyProjectParticipantException();
        if (project.getPending().contains(target)) throw new AlreadyProjectInvitedException();

        project.getPending().add(user);

        return ProjectResponse.builder()
            .project(projectRepository.save(project))
            .build();
    }

    public ProjectResponse updateProject(String projectId, CreateProjectRequest dto, User user) {

        Project project = projectRepository.findById(projectId)
            .filter(p -> p.getParticipants().contains(user))
            .orElseThrow(ProjectNotFoundException::new);

        project.setName(dto.name());

        return ProjectResponse.builder()
            .project(projectRepository.save(project))
            .build();
    }

    public void deleteProject(String projectId, User user) {

        Project project = projectRepository.findById(projectId)
            .filter(p -> p.getParticipants().contains(user))
            .orElseThrow(ProjectNotFoundException::new);

        List<Instance> projects = instanceRepository.findAllByProject(project);
        if (!projects.isEmpty()) throw new ProjectHasInstanceException(projects);

        projectRepository.delete(project);
    }
}
