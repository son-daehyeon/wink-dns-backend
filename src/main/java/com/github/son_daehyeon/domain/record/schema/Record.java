package com.github.son_daehyeon.domain.record.schema;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.DBRef;

import com.github.son_daehyeon.common.database.mongo.BaseSchema;
import com.github.son_daehyeon.domain.user.schema.User;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import software.amazon.awssdk.services.route53.model.RRType;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Record extends BaseSchema {

	@DBRef
	User user;

	String name;
	RRType type;
	long ttl;
	List<String> record;
}
