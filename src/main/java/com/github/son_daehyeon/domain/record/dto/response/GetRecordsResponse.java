package com.github.son_daehyeon.domain.record.dto.response;

import java.util.List;

import com.github.son_daehyeon.domain.record.schema.Record;

import lombok.Builder;

@Builder
public record GetRecordsResponse(

	List<Record> records
) {
}
