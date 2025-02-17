package com.github.son_daehyeon.domain.project.schema;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.DBRef;

import com.github.son_daehyeon.common.database.mongo.BaseSchema;
import com.github.son_daehyeon.domain.user.schema.User;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Project extends BaseSchema {

	String icon;
	String name;

	@DBRef
	List<User> participants;

	@DBRef
	List<User> pending;
}