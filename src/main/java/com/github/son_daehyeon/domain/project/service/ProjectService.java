package com.github.son_daehyeon.domain.project.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.github.son_daehyeon.domain.project.__sub__.instance.repository.InstanceRepository;
import com.github.son_daehyeon.domain.project.__sub__.instance.schema.Instance;
import com.github.son_daehyeon.domain.project.dto.request.CreateProjectRequest;
import com.github.son_daehyeon.domain.project.dto.request.InviteProjectRequest;
import com.github.son_daehyeon.domain.project.dto.response.ProjectResponse;
import com.github.son_daehyeon.domain.project.dto.response.ProjectsResponse;
import com.github.son_daehyeon.domain.project.exception.AlreadyProjectInvitedException;
import com.github.son_daehyeon.domain.project.exception.AlreadyProjectParticipantException;
import com.github.son_daehyeon.domain.project.exception.CannotDeleteProjectException;
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

        List<Project> projects = projectRepository.findAllByParticipantsContains(user);

        if (projects.isEmpty()) {

            projectRepository.save(
                Project.builder()
                    .icon("box")
                    .name("데모 프로젝트")
                    .participants(List.of(user))
                    .pending(List.of())
                    .build()
            );

            projects = projectRepository.findAllByParticipantsContains(user);
        }

        return ProjectsResponse.builder()
            .projects(projects)
            .build();
    }

    public ProjectsResponse invitedProjects(User user) {

        return ProjectsResponse.builder()
            .projects(projectRepository.findAllByPendingContains(user))
            .build();
    }

    public ProjectResponse createProject(CreateProjectRequest dto, User user) {

        Project project = Project.builder()
            .icon(dto.icon())
            .name(dto.name())
            .participants(List.of(user))
            .pending(List.of())
            .build();

        return ProjectResponse.builder()
            .project(projectRepository.save(project))
            .build();
    }

    public ProjectResponse inviteUser(String projectId, InviteProjectRequest dto, User user) {

        Project project = projectRepository.findById(projectId)
            .filter(p -> p.getParticipants().contains(user))
            .orElseThrow(ProjectNotFoundException::new);

        List<User> targets = dto.users()
            .stream()
            .map(userRepository::findById)
            .map((target) -> target.orElseThrow(UserNotFoundException::new))
            .peek(target -> {
                if (project.getParticipants().contains(target)) {
                    throw new AlreadyProjectParticipantException();
                }
            })
            .peek(target -> {
                if (project.getPending().contains(target)) {
                    throw new AlreadyProjectInvitedException();
                }
            })
            .toList();

        project.getPending().addAll(targets);

        return ProjectResponse.builder()
            .project(projectRepository.save(project))
            .build();
    }

    public ProjectResponse acceptInvite(String projectId, User user) {

        Project project = projectRepository.findById(projectId)
            .filter(p -> p.getPending().contains(user))
            .orElseThrow(ProjectNotFoundException::new);

        project.getParticipants().add(user);
        project.getPending().remove(user);

        return ProjectResponse.builder()
            .project(projectRepository.save(project))
            .build();
    }

    public ProjectResponse declineInvite(String projectId, User user) {

        Project project = projectRepository.findById(projectId)
            .filter(p -> p.getPending().contains(user))
            .orElseThrow(ProjectNotFoundException::new);

        project.getPending().remove(user);

        return ProjectResponse.builder()
            .project(projectRepository.save(project))
            .build();
    }

    public ProjectResponse updateProject(String projectId, CreateProjectRequest dto, User user) {

        Project project = projectRepository.findById(projectId)
            .filter(p -> p.getParticipants().contains(user))
            .orElseThrow(ProjectNotFoundException::new);

        project.setIcon(dto.icon());
        project.setName(dto.name());

        return ProjectResponse.builder()
            .project(projectRepository.save(project))
            .build();
    }

    public void deleteProject(String projectId, User user) {

        Project project = projectRepository.findById(projectId)
            .filter(p -> p.getParticipants().contains(user))
            .orElseThrow(ProjectNotFoundException::new);

        if (projectRepository.findAllByParticipantsContains(user).size() <= 1) {

            throw new CannotDeleteProjectException();
        }

        List<Instance> instances = instanceRepository.findAllByProject(project);
        if (!instances.isEmpty()) throw new ProjectHasInstanceException(instances);

        projectRepository.delete(project);
    }
}
