package com.github.son_daehyeon.domain.project.__sub__.instance.schema;

import com.github.son_daehyeon.common.database.mongo.BaseSchema;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Vmid extends BaseSchema {

    int vmid;
}

