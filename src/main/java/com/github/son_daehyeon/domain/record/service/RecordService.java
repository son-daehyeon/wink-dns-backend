package com.github.son_daehyeon.domain.record.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.github.son_daehyeon.domain.record.dto.request.CreateRecordRequest;
import com.github.son_daehyeon.domain.record.dto.request.UpdateRecordRequest;
import com.github.son_daehyeon.domain.record.dto.response.GetRecordResponse;
import com.github.son_daehyeon.domain.record.dto.response.GetRecordsResponse;
import com.github.son_daehyeon.domain.record.exception.DuplicatedRecordNameException;
import com.github.son_daehyeon.domain.record.exception.RecordNotFoundException;
import com.github.son_daehyeon.domain.record.repository.RecordRepository;
import com.github.son_daehyeon.domain.record.schema.Record;
import com.github.son_daehyeon.domain.record.util.Route53Util;
import com.github.son_daehyeon.domain.user.schema.User;

import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.services.route53.model.RRType;

@Service
@RequiredArgsConstructor
public class RecordService {

    private final RecordRepository recordRepository;

    private final Route53Util route53Util;

    public GetRecordsResponse getRecords(User user) {

        List<Record> records = recordRepository.findAllByUser(user);

        return GetRecordsResponse.builder()
            .records(records)
            .build();
    }

    public GetRecordResponse createRecord(CreateRecordRequest dto, User user) {

        if (recordRepository.existsByName(dto.name())) throw new DuplicatedRecordNameException(dto.name());

        Record record = recordRepository.save(
            Record.builder()
                .user(user)
                .name(dto.name() + ".wink.io.kr")
                .type(RRType.valueOf(dto.type()))
                .ttl(dto.ttl())
                .record(dto.records())
                .build()
        );

        route53Util.create(record);

        return GetRecordResponse.builder()
            .record(record)
            .build();
    }

    public GetRecordResponse updateRecord(String recordId, UpdateRecordRequest dto, User user) {

        Record record = recordRepository.findById(recordId)
            .filter(record1 -> record1.getUser().equals(user))
            .orElseThrow(RecordNotFoundException::new);

        record = recordRepository.save(
            Record.builder()
                .id(record.getId())
                .user(record.getUser())
                .name(record.getName())
                .type(RRType.valueOf(dto.type()))
                .ttl(dto.ttl())
                .record(dto.records())
                .build()
        );

        route53Util.update(record);

        return GetRecordResponse.builder()
            .record(record)
            .build();
    }

    public void deleteRecord(String recordId, User user) {

        Record record = recordRepository.findById(recordId)
            .filter(record1 -> record1.getUser().equals(user))
            .orElseThrow(RecordNotFoundException::new);

        recordRepository.delete(record);

        route53Util.delete(record);
    }
}
