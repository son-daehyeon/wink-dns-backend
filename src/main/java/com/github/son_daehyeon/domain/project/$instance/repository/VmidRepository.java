package com.github.son_daehyeon.domain.project.$instance.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.github.son_daehyeon.domain.project.$instance.schema.Vmid;

@Repository
public interface VmidRepository extends MongoRepository<Vmid, String> {
}
