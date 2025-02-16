
package com.github.son_daehyeon.domain.project.__sub__.instance.__sub__.matrix.service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import com.github.son_daehyeon.domain.project.exception.ProjectNotFoundException;
import com.github.son_daehyeon.domain.project.repository.ProjectRepository;
import com.github.son_daehyeon.domain.project.schema.Project;
import com.github.son_daehyeon.domain.project.__sub__.instance.__sub__.matrix.constant.TimeUnit;
import com.github.son_daehyeon.domain.project.__sub__.instance.__sub__.matrix.dto.internal.InstanceMatrix;
import com.github.son_daehyeon.domain.project.__sub__.instance.__sub__.matrix.dto.response.InstanceMatrixResponse;
import com.github.son_daehyeon.domain.project.__sub__.instance.exception.InstanceNotFouncException;
import com.github.son_daehyeon.domain.project.__sub__.instance.repository.InstanceRepository;
import com.github.son_daehyeon.domain.project.__sub__.instance.schema.Instance;
import com.github.son_daehyeon.domain.project.__sub__.instance.util.ProxmoxApi;
import com.github.son_daehyeon.domain.user.schema.User;

import kong.unirest.core.JsonNode;
import kong.unirest.core.json.JSONObject;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InstanceMatrixService {

    private final ProjectRepository projectRepository;
    private final InstanceRepository instanceRepository;

    private final ProxmoxApi proxmoxApi;

    public InstanceMatrixResponse getMatrix(String projectId, String instanceId, TimeUnit timeUnit, User user) {

        Project project = projectRepository.findById(projectId)
            .filter(p -> p.getParticipants().contains(user))
            .orElseThrow(ProjectNotFoundException::new);

        Instance instance = instanceRepository.findById(instanceId)
            .filter(x -> x.getProject().equals(project))
            .orElseThrow(InstanceNotFouncException::new);

        JsonNode response = proxmoxApi.http("/lxc/%d/rrddata?timeframe=%s".formatted(instance.getVmid(), timeUnit.name().toLowerCase()), HttpMethod.GET);

        @SuppressWarnings("unchecked")
        List<InstanceMatrix> matrix = ((List<JSONObject>)response.getArray().toList()).stream()
            .map(data ->
                InstanceMatrix.builder()
                    .time(LocalDateTime.ofEpochSecond(data.getLong("time"), 0, ZoneOffset.ofHours(9)))
                    .maxcpu(data.optDouble("maxcpu", 0))
                    .cpu(data.optDouble("cpu", 0))
                    .maxmem(data.optDouble("maxmem", 0))
                    .mem(data.optDouble("mem", 0))
                    .maxdisk(data.optDouble("maxdisk", 0))
                    .disk(data.optDouble("disk", 0))
                    .diskread(data.optDouble("diskread", 0))
                    .diskwrite(data.optDouble("diskwrite", 0))
                    .netin(data.optDouble("netin", 0))
                    .netout(data.optDouble("netout", 0))
                    .build()
            )
            .toList();

        return InstanceMatrixResponse.builder()
            .matrix(matrix)
            .build();
    }
}
