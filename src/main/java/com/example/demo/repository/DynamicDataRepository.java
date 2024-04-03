package com.example.demo.repository;

import com.example.demo.model.DynamicData;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DynamicDataRepository extends CassandraRepository<DynamicData, String> {
}
