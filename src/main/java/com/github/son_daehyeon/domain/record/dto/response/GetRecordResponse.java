package com.github.son_daehyeon.domain.record.dto.response;

import com.github.son_daehyeon.domain.record.schema.Record;

import lombok.Builder;

@Builder
public record GetRecordResponse(

	Record record
) {
}
