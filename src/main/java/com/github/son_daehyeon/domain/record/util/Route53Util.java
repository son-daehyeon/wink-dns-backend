package com.github.son_daehyeon.domain.record.util;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.github.son_daehyeon.common.api.exception.ApiException;
import com.github.son_daehyeon.common.property.AwsProperty;
import com.github.son_daehyeon.domain.record.schema.Record;

import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.services.route53.Route53Client;
import software.amazon.awssdk.services.route53.model.Change;
import software.amazon.awssdk.services.route53.model.ChangeAction;
import software.amazon.awssdk.services.route53.model.ChangeBatch;
import software.amazon.awssdk.services.route53.model.ChangeResourceRecordSetsRequest;
import software.amazon.awssdk.services.route53.model.InvalidChangeBatchException;
import software.amazon.awssdk.services.route53.model.ResourceRecord;
import software.amazon.awssdk.services.route53.model.ResourceRecordSet;

@Component
@RequiredArgsConstructor
public class Route53Util {

	private final Route53Client client;

	private final AwsProperty awsProperty;

	public void create(Record record) {

		push(
			ChangeBatch.builder()
				.changes(Change.builder()
					.action(ChangeAction.CREATE)
					.resourceRecordSet(ResourceRecordSet.builder()
						.name(record.getName())
						.type(record.getType())
						.ttl(record.getTtl())
						.resourceRecords(record.getRecord().stream()
							.map(value -> ResourceRecord.builder().value(value).build())
							.toList())
						.build())
					.build())
				.build()
		);
	}

	public void update(Record record) {

		push(
			ChangeBatch.builder()
				.changes(Change.builder()
					.action(ChangeAction.UPSERT)
					.resourceRecordSet(ResourceRecordSet.builder()
						.name(record.getName())
						.type(record.getType())
						.ttl(record.getTtl())
						.resourceRecords(record.getRecord().stream()
							.map(value -> ResourceRecord.builder().value(value).build())
							.toList())
						.build())
					.build())
				.build()
		);
	}

	public void delete(Record record) {

		push(
			ChangeBatch.builder()
				.changes(Change.builder()
					.action(ChangeAction.DELETE)
					.resourceRecordSet(ResourceRecordSet.builder()
						.name(record.getName())
						.type(record.getType())
						.ttl(record.getTtl())
						.resourceRecords(record.getRecord().stream()
							.map(value -> ResourceRecord.builder().value(value).build())
							.toList())
						.build())
					.build())
				.build()
		);
	}

	private void push(ChangeBatch changeBatch) {

		ChangeResourceRecordSetsRequest request = ChangeResourceRecordSetsRequest.builder()
			.hostedZoneId(awsProperty.getRoute53().getHostedZoneId())
			.changeBatch(changeBatch)
			.build();

		try {
			client.changeResourceRecordSets(request);
		} catch (InvalidChangeBatchException e) {
			throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}
}
