package com.github.son_daehyeon.domain.project.__sub__.instance.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.github.son_daehyeon.domain.project.__sub__.instance.schema.Vmid;

@Repository
public interface VmidRepository extends MongoRepository<Vmid, String> {

    Optional<Vmid> findTopByOrderByVmidDesc();
}
