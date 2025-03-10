package com.github.son_daehyeon.domain.record.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.github.son_daehyeon.domain.record.schema.Record;
import com.github.son_daehyeon.domain.user.schema.User;

@Repository
public interface RecordRepository extends MongoRepository<Record, String> {

	List<Record> findAllByUser(User user);

	boolean existsByName(String name);
}
