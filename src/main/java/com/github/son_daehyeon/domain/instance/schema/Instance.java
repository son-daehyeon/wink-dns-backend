package com.github.son_daehyeon.domain.instance.schema;

import java.time.LocalDateTime;

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
public class Instance extends BaseSchema {

    String name;
    int vmid;

    @DBRef
    User user;

    LocalDateTime createdAt;

    int core;
    int memory;
    int swap;
    int disk;

    String ip;
}

