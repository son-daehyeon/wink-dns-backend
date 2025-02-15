package com.github.son_daehyeon.domain.instance.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import com.github.son_daehyeon.domain.instance.constant.OsType;
import com.github.son_daehyeon.domain.instance.dto.request.CreateInstanceRequest;
import com.github.son_daehyeon.domain.instance.dto.request.UpdateInstanceRequest;
import com.github.son_daehyeon.domain.instance.dto.response.InstanceResponse;
import com.github.son_daehyeon.domain.instance.dto.response.InstancesResponse;
import com.github.son_daehyeon.domain.instance.exception.CannotShrinkDiskException;
import com.github.son_daehyeon.domain.instance.exception.NotExistsInstanceException;
import com.github.son_daehyeon.domain.instance.repository.InstanceRepository;
import com.github.son_daehyeon.domain.instance.schema.Instance;
import com.github.son_daehyeon.domain.instance.util.ProxmoxApi;
import com.github.son_daehyeon.domain.user.schema.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InstanceService {

    private final InstanceRepository instanceRepository;

    private final ProxmoxApi proxmoxApi;

    public InstancesResponse myInstances(User user) {

        List<Instance> instances = instanceRepository.findAllByUser(user);

        return InstancesResponse.builder()
            .instances(instances)
            .build();
    }

    public InstanceResponse createInstance(CreateInstanceRequest dto, User user) {

        int vmid = nextVmid();
        String ip = generateIp();

        proxmoxApi.http(
            "/lxc",
            HttpMethod.POST,
            Map.entry("ostemplate", "local:vztmpl/" + OsType.valueOf(dto.osType()).getTemplate()),
            Map.entry("vmid", String.valueOf(vmid)),
            Map.entry("cores", String.valueOf(dto.core())),
            Map.entry("features", "nesting=1"),
            Map.entry("hostname", "wink-cloud"),
            Map.entry("memory", String.valueOf(dto.memory())),
            Map.entry("net0", "name=eth0,bridge=vmbr0,ip=%s/16,gw=10.10.0.1".formatted(ip)),
            Map.entry("rootfs", "local-lvm:%d,mountoptions=discard".formatted(dto.disk())),
            Map.entry("ssh-public-keys", dto.publicKey()),
            Map.entry("swap", String.valueOf(dto.swap())),
            Map.entry("unprivileged", "1")
        );

        Instance instance = instanceRepository.save(
            Instance.builder()
                .name(dto.name())
                .vmid(vmid)
                .user(user)
                .createdAt(LocalDateTime.now())
                .core(dto.core())
                .memory(dto.memory())
                .swap(dto.swap())
                .disk(dto.disk())
                .ip(ip)
                .build()
        );

        return InstanceResponse.builder()
            .instance(instance)
            .build();
    }

    public InstanceResponse updateInstance(String id, UpdateInstanceRequest dto, User user) {

        Instance instance = instanceRepository.findById(id)
            .filter(x -> x.getUser().equals(user))
            .orElseThrow(NotExistsInstanceException::new);

        if (instance.getDisk() > dto.disk()) throw new CannotShrinkDiskException();

        instance.setName(dto.name());
        instance = instanceRepository.save(instance);

        if (instance.getCore() != dto.core() || instance.getMemory() != dto.memory() || instance.getSwap() != dto.swap()) {
            proxmoxApi.http(
                "/lxc/%d/config".formatted(instance.getVmid()),
                HttpMethod.PUT,
                Map.entry("cores", String.valueOf(dto.core())),
                Map.entry("memory", String.valueOf(dto.memory())),
                Map.entry("swap", String.valueOf(dto.swap()))
            );

            instance.setCore(dto.core());
            instance.setMemory(dto.memory());
            instance.setSwap(dto.swap());
            instance = instanceRepository.save(instance);
        }

        if (instance.getDisk() < dto.disk()) {
            proxmoxApi.http(
                "/lxc/%d/resize".formatted(instance.getVmid()),
                HttpMethod.PUT,
                Map.entry("disk", "rootfs"),
                Map.entry("size", "%dG".formatted(dto.disk()))
            );

            instance.setDisk(dto.disk());
            instance = instanceRepository.save(instance);
        }

        return InstanceResponse.builder()
            .instance(instance)
            .build();
    }

    public void deleteInstance(String id, User user) {

        Instance instance = instanceRepository.findById(id)
            .filter(x -> x.getUser().equals(user))
            .orElseThrow(NotExistsInstanceException::new);

        proxmoxApi.http("/lxc/%d?destroy-unreferenced-disks=1&force=1&purge=1".formatted(instance.getVmid()), HttpMethod.DELETE);

        instanceRepository.delete(
            instanceRepository.findById(id)
                .filter(x -> x.getUser().equals(user))
                .orElseThrow(NotExistsInstanceException::new)
        );
    }

    private int nextVmid() {

        return instanceRepository.findTopByOrderByVmidDesc().map(Instance::getVmid).orElse(1000) + 1;
    }

    private String generateIp() {

        String ip;
        Set<String> excludedIps = Set.of("10.10.0.0", "10.10.0.1", "10.10.255.255");

        do {
            int thirdOctet = new Random().nextInt(256);
            int fourthOctet = new Random().nextInt(256);

            ip = "10.10." + thirdOctet + "." + fourthOctet;

        } while (excludedIps.contains(ip) || instanceRepository.existsByIp(ip));

        return ip;
    }
}
