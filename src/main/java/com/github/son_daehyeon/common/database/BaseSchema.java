package com.github.son_daehyeon.common.database;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@Document
public abstract class BaseSchema {

	@Id
	String id;

	@Override
	public boolean equals(Object obj) {

		if (this == obj) {
			return true;
		}

		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}

		BaseSchema that = (BaseSchema) obj;

		return id.equals(that.id);
	}

	@Override
	public int hashCode() {

		return id.hashCode();
	}
}
