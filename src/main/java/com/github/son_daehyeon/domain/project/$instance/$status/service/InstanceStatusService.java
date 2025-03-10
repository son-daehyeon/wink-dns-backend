package com.github.son_daehyeon.domain.project.$instance.$status.service;

import java.util.Map;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import com.github.son_daehyeon.domain.project.exception.ProjectNotFoundException;
import com.github.son_daehyeon.domain.project.repository.ProjectRepository;
import com.github.son_daehyeon.domain.project.schema.Project;
import com.github.son_daehyeon.domain.project.$instance.$status.dto.response.InstanceStatusResponse;
import com.github.son_daehyeon.domain.project.$instance.$status.exception.InstanceAlreadyRunningException;
import com.github.son_daehyeon.domain.project.$instance.$status.exception.InstanceIsNotRunningException;
import com.github.son_daehyeon.domain.project.$instance.exception.InstanceNotFouncException;
import com.github.son_daehyeon.domain.project.$instance.repository.InstanceRepository;
import com.github.son_daehyeon.domain.project.$instance.schema.Instance;
import com.github.son_daehyeon.domain.project.$instance.util.ProxmoxApi;
import com.github.son_daehyeon.domain.user.schema.User;

import kong.unirest.core.json.JSONObject;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InstanceStatusService {

    private final ProjectRepository projectRepository;
    private final InstanceRepository instanceRepository;

    private final ProxmoxApi proxmoxApi;

    public InstanceStatusResponse currentState(String projectId, String instanceId, User user) {

        Project project = projectRepository.findById(projectId)
            .filter(p -> p.getParticipants().contains(user))
            .orElseThrow(ProjectNotFoundException::new);

        Instance instance = instanceRepository.findById(instanceId)
            .filter(x -> x.getProject().equals(project))
            .orElseThrow(InstanceNotFouncException::new);

        JSONObject response = proxmoxApi.http("/lxc/%d/status/current".formatted(instance.getVmid()), HttpMethod.GET).orElseThrow().getObject();

        return InstanceStatusResponse.builder()
            .running(response.getString("status").equals("running"))
            .uptime(response.getInt("uptime"))
            .build();
    }

    public void start(String projectId, String instanceId, User user) {

        Project project = projectRepository.findById(projectId)
            .filter(p -> p.getParticipants().contains(user))
            .orElseThrow(ProjectNotFoundException::new);

        Instance instance = instanceRepository.findById(instanceId)
            .filter(x -> x.getProject().equals(project))
            .orElseThrow(InstanceNotFouncException::new);

        checkAlreadyRunning(instance);

        proxmoxApi.http("/lxc/%d/status/start".formatted(instance.getVmid()), HttpMethod.POST);
        proxmoxApi.http("/lxc/%d/config".formatted(instance.getVmid()), HttpMethod.PUT, Map.entry("onboot", "1"));
    }

    public void shutdown(String projectId, String instanceId, User user) {

        Project project = projectRepository.findById(projectId)
            .filter(p -> p.getParticipants().contains(user))
            .orElseThrow(ProjectNotFoundException::new);

        Instance instance = instanceRepository.findById(instanceId)
            .filter(x -> x.getProject().equals(project))
            .orElseThrow(InstanceNotFouncException::new);

        checkNotRunning(instance);

        proxmoxApi.http("/lxc/%d/status/shutdown".formatted(instance.getVmid()), HttpMethod.POST);
        proxmoxApi.http("/lxc/%d/config".formatted(instance.getVmid()), HttpMethod.PUT, Map.entry("onboot", "0"));
    }

    public void stop(String projectId, String instanceId, User user) {

        Project project = projectRepository.findById(projectId)
            .filter(p -> p.getParticipants().contains(user))
            .orElseThrow(ProjectNotFoundException::new);

        Instance instance = instanceRepository.findById(instanceId)
            .filter(x -> x.getProject().equals(project))
            .orElseThrow(InstanceNotFouncException::new);

        checkNotRunning(instance);

        proxmoxApi.http("/lxc/%d/status/stop".formatted(instance.getVmid()), HttpMethod.POST);
        proxmoxApi.http("/lxc/%d/config".formatted(instance.getVmid()), HttpMethod.PUT, Map.entry("onboot", "0"));
    }

    private void checkAlreadyRunning(Instance instance) {

        JSONObject response = proxmoxApi.http("/lxc/%d/status/current".formatted(instance.getVmid()), HttpMethod.GET).orElseThrow().getObject();
        boolean running = response.getString("status").equals("running");

        if (running) throw new InstanceAlreadyRunningException();
    }

    public void checkNotRunning(Instance instance) {

        JSONObject response = proxmoxApi.http("/lxc/%d/status/current".formatted(instance.getVmid()), HttpMethod.GET).orElseThrow().getObject();
        boolean running = response.getString("status").equals("running");

        if (!running) throw new InstanceIsNotRunningException();
    }
}
