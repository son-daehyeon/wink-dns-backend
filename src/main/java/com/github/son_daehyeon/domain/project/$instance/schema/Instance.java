package com.github.son_daehyeon.domain.project.$instance.schema;

import org.springframework.data.mongodb.core.mapping.DBRef;

import com.github.son_daehyeon.common.database.mongo.BaseSchema;
import com.github.son_daehyeon.domain.project.schema.Project;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Instance extends BaseSchema {

    String name;
    int vmid;

    @DBRef
    Project project;

    int core;
    int memory;
    int swap;
    int disk;

    String ip;
}

