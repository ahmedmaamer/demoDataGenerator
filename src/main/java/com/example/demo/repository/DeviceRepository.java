package com.example.demo.repository;

import com.example.demo.model.Device;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DeviceRepository extends CassandraRepository<Device, UUID> {

}
