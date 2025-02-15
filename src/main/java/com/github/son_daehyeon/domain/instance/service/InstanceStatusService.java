package com.github.son_daehyeon.domain.instance.service;

import java.util.Map;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import com.github.son_daehyeon.domain.instance.dto.response.InstanceStatusResponse;
import com.github.son_daehyeon.domain.instance.exception.InstanceAlreadyRunningException;
import com.github.son_daehyeon.domain.instance.exception.InstanceIsNotRunningException;
import com.github.son_daehyeon.domain.instance.exception.NotExistsInstanceException;
import com.github.son_daehyeon.domain.instance.repository.InstanceRepository;
import com.github.son_daehyeon.domain.instance.schema.Instance;
import com.github.son_daehyeon.domain.instance.util.ProxmoxApi;
import com.github.son_daehyeon.domain.user.schema.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InstanceStatusService {

    private final InstanceRepository instanceRepository;

    private final ProxmoxApi proxmoxApi;

    public InstanceStatusResponse currentState(String id, User user) {

        Instance instance = instanceRepository.findById(id)
            .filter(x -> x.getUser().equals(user))
            .orElseThrow(NotExistsInstanceException::new);

        Map<String, Object> response = proxmoxApi.http("/lxc/%d/status/current".formatted(instance.getVmid()), HttpMethod.GET);

        return InstanceStatusResponse.builder()
            .running(response.get("status").equals("running"))
            .uptime((int) response.get("uptime"))
            .build();
    }

    public void start(String id, User user) {

        Instance instance = instanceRepository.findById(id)
            .filter(x -> x.getUser().equals(user))
            .orElseThrow(NotExistsInstanceException::new);

        checkAlreadyRunning(instance);

        proxmoxApi.http("/lxc/%d/status/start".formatted(instance.getVmid()), HttpMethod.POST);
    }

    public void shutdown(String id, User user) {

        Instance instance = instanceRepository.findById(id)
            .filter(x -> x.getUser().equals(user))
            .orElseThrow(NotExistsInstanceException::new);

        checkNotRunning(instance);

        proxmoxApi.http("/lxc/%d/status/shutdown".formatted(instance.getVmid()), HttpMethod.POST);
    }

    public void stop(String id, User user) {

        Instance instance = instanceRepository.findById(id)
            .filter(x -> x.getUser().equals(user))
            .orElseThrow(NotExistsInstanceException::new);

        checkNotRunning(instance);

        proxmoxApi.http("/lxc/%d/status/stop".formatted(instance.getVmid()), HttpMethod.POST);
    }

    public void restart(String id, User user) {

        Instance instance = instanceRepository.findById(id)
            .filter(x -> x.getUser().equals(user))
            .orElseThrow(NotExistsInstanceException::new);

        checkNotRunning(instance);

        proxmoxApi.http("/lxc/%d/status/reboot".formatted(instance.getVmid()), HttpMethod.POST);
    }

    private void checkAlreadyRunning(Instance instance) {

        Map<String, Object> response = proxmoxApi.http("/lxc/%d/status/current".formatted(instance.getVmid()), HttpMethod.GET);
        boolean running = response.get("status").equals("running");

        if (running) throw new InstanceAlreadyRunningException();
    }

    public void checkNotRunning(Instance instance) {

        Map<String, Object> response = proxmoxApi.http("/lxc/%d/status/current".formatted(instance.getVmid()), HttpMethod.GET);
        boolean running = response.get("status").equals("running");

        if (!running) throw new InstanceIsNotRunningException();
    }
}
